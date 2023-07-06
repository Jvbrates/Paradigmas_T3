-- public."TIPO_ATIVO_FINANCEIRO" definition

-- Drop table

-- DROP TABLE public."TIPO_ATIVO_FINANCEIRO";

CREATE TABLE public."TIPO_ATIVO_FINANCEIRO" (
	id int4 NOT NULL DEFAULT nextval('tipo_ativo_financeiro_id_seq'::regclass),
	alias bpchar(150) NOT NULL,
	CONSTRAINT tipo_ativo_financeiro_pkey PRIMARY KEY (id)
);


-- public."ATIVO_FINANCEIRO" definition

-- Drop table

-- DROP TABLE public."ATIVO_FINANCEIRO";

CREATE TABLE public."ATIVO_FINANCEIRO" (
	id int4 NOT NULL DEFAULT nextval('ativo_financeiro_id_seq'::regclass),
	ticket bpchar(100) NOT NULL,
	nome_empresa bpchar(100) NOT NULL,
	tipo_ativo_financeiro int4 NOT NULL,
	CONSTRAINT ativo_financeiro_nome_empresa_key UNIQUE (nome_empresa),
	CONSTRAINT ativo_financeiro_pkey PRIMARY KEY (id),
	CONSTRAINT ativo_financeiro_ticket_key UNIQUE (ticket)
);


-- public."ATIVO_FINANCEIRO" foreign keys

ALTER TABLE public."ATIVO_FINANCEIRO" ADD CONSTRAINT pk_tipo FOREIGN KEY (tipo_ativo_financeiro) REFERENCES public."TIPO_ATIVO_FINANCEIRO"(id) ON DELETE CASCADE;


-- public."USUARIO" definition

-- Drop table


-- Inicialmente pensei em fazer com usuario com login, no fim não deu tempo, então o usuario está hardcoded para id=1
-- DROP TABLE public."USUARIO";

CREATE TABLE public."USUARIO" (
	id int4 NOT NULL DEFAULT nextval('usuario_id_seq'::regclass),
	username bpchar(100) NOT NULL,
	passwd bpchar(100) NOT NULL,
	saldo float8 NOT NULL,
	CONSTRAINT usuario_pkey PRIMARY KEY (id),
	CONSTRAINT usuario_username_key UNIQUE (username)
);


-- public."COTACAO" definition

-- Drop table

-- DROP TABLE public."COTACAO";

CREATE TABLE public."COTACAO" (
	id int4 NOT NULL DEFAULT nextval('cotacao_id_seq'::regclass),
	id_ativo int4 NOT NULL,
	dia date NULL,
	valor float8 NOT NULL,
	CONSTRAINT cotacao_pkey PRIMARY KEY (id)
);


-- public."COTACAO" foreign keys

ALTER TABLE public."COTACAO" ADD CONSTRAINT pk_ativo FOREIGN KEY (id_ativo) REFERENCES public."ATIVO_FINANCEIRO"(id) ON DELETE CASCADE;




-- public."HISTORICO" definition

-- Drop table

-- DROP TABLE public."HISTORICO";

CREATE TABLE public."HISTORICO" (
	id int4 NOT NULL DEFAULT nextval('historico_id_seq'::regclass),
	id_user int4 NOT NULL,
	id_ativo int4 NOT NULL,
	valor_unit float8 NOT NULL,
	dia date NOT NULL,
	quantidade int4 NOT NULL,
	compra bool NOT NULL,
	CONSTRAINT historico_dia_key UNIQUE (dia),
	CONSTRAINT historico_pkey PRIMARY KEY (id)
);


-- public."HISTORICO" foreign keys

ALTER TABLE public."HISTORICO" ADD CONSTRAINT pk_ativo FOREIGN KEY (id_ativo) REFERENCES public."ATIVO_FINANCEIRO"(id) ON DELETE CASCADE;
ALTER TABLE public."HISTORICO" ADD CONSTRAINT pk_user FOREIGN KEY (id_user) REFERENCES public."USUARIO"(id) ON DELETE CASCADE;










-- public."INVENTARIO_CARTEIRA" definition

-- Drop table

-- DROP TABLE public."INVENTARIO_CARTEIRA";

CREATE TABLE public."INVENTARIO_CARTEIRA" (
	id int4 NOT NULL DEFAULT nextval('inventario_carteira_id_seq'::regclass),
	id_user int4 NOT NULL,
	id_ativo int4 NOT NULL,
	quantidade int4 NOT NULL,
	CONSTRAINT inventario_carteira_pkey PRIMARY KEY (id)
);


-- public."INVENTARIO_CARTEIRA" foreign keys

ALTER TABLE public."INVENTARIO_CARTEIRA" ADD CONSTRAINT pk_ativo FOREIGN KEY (id_ativo) REFERENCES public."ATIVO_FINANCEIRO"(id) ON DELETE CASCADE;
ALTER TABLE public."INVENTARIO_CARTEIRA" ADD CONSTRAINT pk_user FOREIGN KEY (id_user) REFERENCES public."USUARIO"(id) ON DELETE CASCADE;




