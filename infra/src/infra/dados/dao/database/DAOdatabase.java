package infra.dados.dao.database;

import infra.dados.ExceptionInfraDados;
import infra.dados.dao.DAO;
import infra.entidades.Registro;
//import infra.dados.dao.database.connectionDB;
import infra.negocios.DadoNaoEncontrado;

import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.sql.RowSet;



public abstract class DAOdatabase<T extends Registro> implements DAO<T> {
		
	final static Connection conn =  connectionDB.getConn();
	
	protected Class<T> Tclass;

	public DAOdatabase(Class<T> tclass) {
		this.Tclass = tclass;
	}
	
	
	public String getTableName() {

		return Tclass.getSimpleName().toUpperCase();
	}
	
	
	/* Get an array of T objects from a Result Set*/
	protected Collection<T> CollectionFromRSet(ResultSet rset) throws SQLException{
		ArrayList<T> retorno = new ArrayList<T>();
		
		ResultSetMetaData rsMetaData = rset.getMetaData();
		int countColumns = rsMetaData.getColumnCount();

		ArrayList<String> MetaMap = new ArrayList<String>();
		for(int i = 1; i <= countColumns; i ++) {
			MetaMap.add(rsMetaData.getColumnName(i));
		}
		
		try {
			while (rset.next()) {

				T ed = Tclass.getDeclaredConstructor().newInstance();

				Field[] fd = Tclass.getDeclaredFields();

				for (Field field : fd) {
					field.setAccessible(true);
					int indice = MetaMap.indexOf(field.getName());
					if (indice != -1) {
						if (field.getType().getInterfaces().length > 0 && field.getType().getInterfaces()[0].getSimpleName().equals("Registro")) {
							field.set(ed, field.getClass().getDeclaredConstructor().newInstance());
							Registro foreing = (Registro) (field.get(ed));
							foreing.setId((long) rset.getObject(indice + 1));

						} else {
							field.set(ed, rset.getObject(indice + 1));
						}
					}
				}

				retorno.add(ed);
			}
		} catch (IllegalAccessException | NoSuchMethodException a){
			a.printStackTrace();
		} catch (InvocationTargetException | InstantiationException e) {
			throw new RuntimeException(e);
		}

		return retorno;
	}
	
	/*
	 * Percorre os parametros do registro.
	 * Caso id esteja definido raise exception
	 * Caso o parametro seja um Inteiro/Long ou String, coloca o valor no sql
	 * Caso o parametro seja um object get_id for insert as foreing key in the table
	 * Array são ignoradas e devem ser tratadas pela classe filha*/
	public void adicionar(T t) throws ExceptionInfraDados {
		
		
		
		String prefix_sql = "INSERT INTO \""+getTableName()+"\" (";
		String suffix_sql = " VALUES ( ";
		
		
	    Field[] allFields = t.getClass().getDeclaredFields();
	    try {

			for (Field field : allFields) {
				field.setAccessible(true);

				System.out.println(field.getName());
				// Por favor que este IF funcione
				if (!(field.getType().toString().equalsIgnoreCase("class java.util.ArrayList"))
						&& field.get(t) != null
						&& field.getName() != "id") {


					prefix_sql += (field.getName() + ", ");
					suffix_sql += "?, ";
				}
			}
		} catch (IllegalAccessException ignored){}
	    //Aqui remove a virgula
	    prefix_sql = prefix_sql.substring(0, prefix_sql.length()-2);
	    suffix_sql = suffix_sql.substring(0, suffix_sql.length()-2);
	    
	    //fecha o parenteses
	    prefix_sql+=")";
	    suffix_sql+=")";
	    
	    //Une as partes do sql
	    String sql = prefix_sql+suffix_sql;
	    
	    System.out.print(sql);
	    
	    //throw new IllegalAccessException("Exception message");
	    
	    try {
	    	
			PreparedStatement p = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			System.out.println("Passou do prepared statement");
			System.out.println(sql);
			int i = 1;
		    for(Field field: allFields) {
		        field.setAccessible(true);

				System.out.println(field.getName());
		       switch(field.getType().toString()) {
			       
			       case "class java.util.ArrayList":{
			    	   break;
			       }
			       case "int":{
			    	   if(field.getName() != "id") {

				    	   p.setInt(i, field.getInt(t));
				    	   i++;
				    	   } else {
				    		   if((long)(field.get(t)) != 0) {
				    			   throw new ExceptionInfraDados("Id definido em um item i, isto não deve ocorrer "+ field.get(t));
				    		   }
				    	   }
			    	   break;}
			       case "long":{
			    	   if(field.getName() != "id") {
			    	   p.setLong(i, field.getLong(t));
			    	   i++;
			    	   } else {
			    		   if((long)(field.get(t)) != 0) {
			    			   throw new ExceptionInfraDados("Id definido, isto não deve ocorrer "+ field.get(t));
			    		   }
			    	   }
			    	   break;
			       }
			       case "class java.lang.String":{

			    	   p.setString(i, (String)field.get(t));
			    	   i++;
			    	   break;
			       }

				   case "double":{
					   p.setDouble(i, (Double) field.get(t));
					   i++;
					   break;
				   }

				   case "class java.sql.Date" :{
					   p.setDate(i, (Date) field.get(t));
					   i++;
					   break;
				   }

				   case "boolean":{
					   p.setBoolean(i, field.getBoolean(t));
					   i++;
					   break;
				   }

			       default :{
						System.out.println(field.getType().toString());
			    	   long id = (
			    			   (
			    					   (Registro)(
			    							   field.get(t)
			    							   )
	    					   )
			    			   .getId()
			    			   );
			    	   p.setLong(i, id);
			    	   i++;
			       }
			       
		       }
		        
		    }

		    
		    int r = p.executeUpdate();

		    try {
				ResultSet generatedKeys = p.getGeneratedKeys();
	            if (generatedKeys.next()) {
	            	long id = generatedKeys.getLong(1);
	                t.setId(id);

	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        } catch (SQLException e) {
				throw new ExceptionInfraDados("Erro consulta SQL na Infra");
			}




		} catch (SQLException e) {
			throw new ExceptionInfraDados("Erro consulta SQL na Infra");
		} catch (IllegalAccessException ignored){}
	    
	  return;
	    
	    
	};
	
	/*Remove com base no ID*/
	public void remover(T t) throws DadoNaoEncontrado{
		if(t.getId() == 0) {
			throw new ExceptionInfraDados("Id não definido em operação de Remover");
		}
		
		String sql = "DELETE FROM \""+getTableName()+"\" WHERE ID = ?";
		try {
			PreparedStatement p = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			p.setLong(1, t.getId());
			// System.out.println(sql+t.getId());
			int affectd_rows = p.executeUpdate();
			if(affectd_rows == 0) {
				throw new DadoNaoEncontrado();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ExceptionInfraDados("SQL Exception");
		}
	    
		
	};
	
	//abstract protected Collection<T> CollectionFromRSet(ResultSet rset) throws Exception;
	
	public Collection<T> buscar(T t){
		String sql = "SELECT * FROM \""+getTableName()+"\" WHERE ";
		
		Field[] allFields = null;

		try {
		if(t != null) {
		
			allFields = t.getClass().getDeclaredFields();
	    
	    
			for(Field field: allFields) {
				
	        field.setAccessible(true);
	        
	        // Por favor que este IF funcione
		    if(!(field.getType().toString().equalsIgnoreCase("class java.util.ArrayList")) 
		    		&& field.get(t) != null) 
			    {
		    		if(field.getName() == "id" && field.getLong(t) == 0) {
		    			continue;
	    			}
		    		
		    		if(field.getType().getName() == "int" && field.getInt(t) == 0)
		    			continue;
		    		if(field.getType().getName() == "long" && field.getLong(t) == 0)
		    				continue;
					if(field.getType().getName() == "double" && field.getDouble(t) == 0)
							continue;
		    		
			    	// System.out.println(field.getName()+"| " +field.getType().toString() + ":" + field.get(t));
			    	sql+=field.getName()+" ";
			    	
			    	switch(field.getType().toString()) {
				       
				       case "class java.util.ArrayList":{
				    	   break;
				       }
				       case "int":
				       case "long":{
				    	   
				    	   sql+=" = ? AND ";
				    			  
				    	   break;
				       }
				       case "class java.lang.String":{
				    	   sql+= "LIKE ? AND ";
				    	   break;
				       }
						case "class java.sql.Date":{
							sql+=" = ? AND ";
							break;
						}
				       
				       default :{
				    	   sql+=" = ? AND ";
				       }
				       
			       }
		    	}
		    
			}
		}
				
		sql += " 1 = 1";
		
		// System.out.println(sql);
		
			try {
				PreparedStatement p = conn.prepareStatement(sql);

				int i = 1;
				if (t != null) {
					for (Field field : allFields) {
						field.setAccessible(true);

						if (field.get(t) == null || (field.getName() == "id" && field.getLong(t) == 0))
							continue;

						if (field.getType().getName() == "int" && field.getInt(t) == 0)
							continue;
						if (field.getType().getName() == "long" && field.getLong(t) == 0)
							continue;
						if (field.getType().getName() == "double" && field.getDouble(t) == 0)
							continue;


						switch (field.getType().toString()) {
							case "class java.util.ArrayList" -> {
							}
							case "int" -> {
								p.setInt(i, field.getInt(t));
								i++;
							}
							case "long" -> {
								p.setLong(i, field.getLong(t));
								i++;

							}
							case "class java.lang.String" -> {
								// System.out.println(field.get(t)+ field.getName());
								p.setString(i, "%" + (String) field.get(t) + "%");
								i++;
							}
							case "class java.sql.Date" ->{
								p.setDate(i, (java.sql.Date) field.get(t));
								i++;
							}

							case "double" ->{
								p.setDouble(i, (double) field.get(t));
								i++;
							}

							default -> {
								 System.out.println( field.getName()+" _|" + field.getType().toString() + " " + i);
								long id = (
										(
												(Registro) (
														field.get(t)
												)
										)
												.getId()
								);
								p.setLong(i, id);
								i++;

							}
						}

					}
				}
				 System.out.println(sql);

				ResultSet r = p.executeQuery();
				Collection<T> cr = CollectionFromRSet(r);


				return cr;

			} catch (SQLException s){
				throw new ExceptionInfraDados("SQL Exception");
			}

		}catch (IllegalAccessException ignored){}
		return null;
	};

	public Collection<T> buscarDataRangebuscar(T t, java.sql.Date from, java.sql.Date to) throws DadoNaoEncontrado{
		/* Este método é semelhante ao buscar ;Entretanto, quando ele encontrar uma variável Date
		* ele não adicionará um ? à string de busca, e adicionara ao final da string as condições
		* de busca para Date dentro do range passado como parametro
		* */

		// Create SQL command

		String sql = "SELECT * FROM \""+getTableName()+"\" WHERE ";

		Field[] allFields = null;
		String date_name = null;

	try {
		if(t != null) {

			allFields = t.getClass().getDeclaredFields();


			for(Field field: allFields) {


				field.setAccessible(true);

				// Por favor que este IF funcione
				System.out.println(field.getType().toString());

				if(field.getType().toString().equalsIgnoreCase( "class java.sql.Date")) {
					System.out.println("AAA");
					date_name = field.getName();
				}

				if(!(field.getType().toString().equalsIgnoreCase("class java.util.ArrayList"))
						&& field.get(t) != null)
				{
					if(field.getName() == "id" && field.getLong(t) == 0) {
						continue;
					}

					if(field.getType().getName() == "int" && field.getInt(t) == 0)
						continue;
					if(field.getType().getName() == "long" && field.getLong(t) == 0)
						continue;
					if(field.getType().getName() == "double" && field.getDouble(t) == 0)
						continue;
					if(field.getType().getName() == "boolean")
						continue;

					// System.out.println(field.getName()+"| " +field.getType().toString() + ":" + field.get(t));
					sql+=field.getName()+" ";



					switch(field.getType().toString()) {

						case "class java.util.ArrayList":{
							break;
						}
						case "int":
						case "double":
						case "long":{

							sql+=" = ? AND ";

							break;
						}
						case "class java.lang.String":{
							sql+= "LIKE ? AND ";
							break;
						}
						case "class java.sql.Date":{
							//Do nothing
							break;

						}

						default :{
							sql+=" = ? AND ";
						}

					}
				}

			}
		}


		if (date_name != null) {
			sql += date_name+" >= '" + from.toString() + "' AND "+date_name+" <= '" + to.toString()+ "' AND ";
		} else {
			System.out.println("NULL");
		}
		sql += "  1 = 1";

		System.out.println(sql);


		try {
			PreparedStatement p = conn.prepareStatement(sql);

			int i = 1;
			if (t != null) {
				for (Field field : allFields) {
					field.setAccessible(true);

					if (field.get(t) == null || (field.getName() == "id" && field.getLong(t) == 0))
						continue;

					if (field.getType().getName() == "int" && field.getInt(t) == 0)
						continue;
					if (field.getType().getName() == "long" && field.getLong(t) == 0)
						continue;
					if(field.getType().getName() == "double" && field.getDouble(t) == 0)
						continue;
					if(field.getType().getName() == "boolean")
						continue;

					switch (field.getType().toString()) {
						case "class java.util.ArrayList" -> {
						}
						case "int" -> {
							p.setInt(i, field.getInt(t));
							i++;
						}
						case "long" -> {
							p.setLong(i, field.getLong(t));
							i++;

						}

						case "double" -> {
							p.setDouble(i, field.getDouble(t));
							i++;
						}
						case "class java.lang.String" -> {
							System.out.println("Adding String");
							p.setString(i, "%" + (String) field.get(t) + "%");
							i++;
						}
						case "class java.sql.Date" ->{
							System.out.println("No Adding Date");
							//p.setDate(i, (Date) field.get(t));
							//i++;
							break;

						}
						case "boolean" -> {
							p.setBoolean(i, field.getBoolean(t));
							i++;
							break;
						}


						default -> {
							// System.out.println( field.getName()+" _|" + field.getType().toString() + " " + i);
							long id = (
									(
											(Registro) (
													field.get(t)
											)
									)
											.getId()
							);
							System.out.println("Default__");
							p.setLong(i, id);
							i++;

						}
					}



				}
			}
			// System.out.println(sql);

			ResultSet r = p.executeQuery();

			Collection<T> cr = CollectionFromRSet(r);

			if (cr.size() == 0) {
				throw new DadoNaoEncontrado();
			}

			return cr;

		} catch (SQLException s){
			s.printStackTrace();
		} catch (DadoNaoEncontrado d) {
			throw new DadoNaoEncontrado();
		}
	} catch (IllegalAccessException ignored){}

		return null;

	}
	
	public Collection<T> buscarTodos() throws ExceptionInfraDados{
		
		 Collection<T> a = buscar(null); 
		
		return a;
		
	};
	
	public void alterar(T e) throws DadoNaoEncontrado, IllegalArgumentException{
		String sql = "UPDATE \""+getTableName()+"\" SET ";
		String suffix_sql = " WHERE id="+e.getId();
		
		
		
		Field[] All_fields =Tclass.getDeclaredFields();

		try {
			for (Field field : All_fields) {
				field.setAccessible(true);
				if (field.get(e) != null) {

					switch (field.getType().toString()) {
						case "long": {
							if ((long) (field.get(e)) != 0 && field.getName() != "id") {
								sql += field.getName() + "=?, ";
								// System.out.println(field.get(e));
							}
							break;
						}
						case "int": {

							if ((int) (field.get(e)) != 0 && field.getName() != "id") {
								sql += field.getName() + "=?, ";
							}
							break;
						}

						case "class java.util.ArrayList": {
							sql += field.getName() + "=?, ";
							break;
						}
						default: {
							sql += field.getName() + "=?, ";
							break;
						}

					}

				}
			}


			try {
				sql = sql.substring(0, sql.length() - 2);
				sql += suffix_sql;

				PreparedStatement p = conn.prepareStatement(sql);
				int i = 1;
				for (Field field : All_fields) {
					field.setAccessible(true);
					if (field.get(e) != null) {

						switch (field.getType().toString()) {
							case "long": {
								if ((long) (field.get(e)) != 0 && field.getName() != "id") {
									// System.out.println(field.getLong(e));
									p.setLong(i, field.getLong(e));
									i++;
								}
								break;
							}
							case "int": {
								// System.out.println(field.getInt(e));

								if ((int) (field.get(e)) != 0 && field.getName() != "id") {
									p.setInt(i, field.getInt(e));
									i++;
								}
								break;
							}
							case "class java.lang.String": {
								p.setString(i, (String) (field.get(e)));
								i++;
								break;
							}

							case "class java.sql.Date": {
								p.setDate(i, (Date) field.get(e));
								i++;
								break;
							}


							case "class java.util.ArrayList": {
								break;
							}
							default: {
								p.setLong(i, (
										(Registro) (field.get(e)))
										.getId()
								);
								i++;
								break;
							}

						}

					}
				}


				// System.out.println(sql);


				p.execute();

			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		} catch (IllegalAccessException | IllegalArgumentException ignored){}
		
		
	};
}
