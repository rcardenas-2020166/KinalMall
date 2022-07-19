
/*
	Nombre del Programador: Rodrigo Gerardo Cárdenas Monroy
    Código Técnico: IN5AV
    Carné: 2020166
    
    Fecha de Creación:
		05/05/2021
	
    Modificaciones:
		06/05/2021
        07/05/2021
        03/06/2021
        06/06/2021
        16/06/2021
        09/07/2021
        05/08/2021
        
	Revisión:
		05/08/2021
*/

drop database if exists dbkinalmall2020166;
create database DBKinalMall2020166;
use DBKinalMall2020166;


/* CREACION DE LAS ENTIDADES */

-- Departamentos
create table Departamentos(
	codigoDepartamento int auto_increment not null,
    nombreDepartamento varchar(45) not null,
    primary key PK_codigoDepartamento (codigoDepartamento)
);


-- Cargos
create table Cargos(
	codigoCargo int auto_increment not null,
    nombreCargo varchar(45) not null,
    primary key PK_codigoCargo (codigoCargo)
);


-- Horarios
create table Horarios(
	codigoHorario int auto_increment not null,
    horaEntrada varchar(10) not null,
    horaSalida varchar(10) not null,
	lunes boolean,
    martes boolean,
    miercoles boolean,
    jueves boolean,
    viernes boolean,
    primary key PK_codigoHorario (codigoHorario)
);


-- Administracion
create table Administracion(
	codigoAdministracion int auto_increment not null,
    direccion varchar(45) not null,
    telefono varchar(8) not null,
    primary key PK_codigoAdministracion (codigoAdministracion)
);


-- Empleados
create table Empleados(
	codigoEmpleado int auto_increment not null,
    nombresEmpleado varchar(45) not null,
    apellidosEmpleado varchar(45) not null,
	correoElectronico varchar(45) not null,
    telefonoEmpleado varchar(8) not null,
    fechaContratacion date not null,
    sueldo double(11,2) not null,
	codigoDepartamento int not null,
    codigoCargo int not null,
    codigoHorario int not null,
    codigoAdministracion int not null,
    primary key PK_codigoEmpleado (codigoEmpleado),
    constraint FK_Empleados_Departamentos foreign key (codigoDepartamento)
		references Departamentos (codigoDepartamento),
    constraint FK_Empleados_Cargos foreign key (codigoCargo)
		references Cargos (codigoCargo),
	constraint FK_Empleados_Horarios foreign key (codigoHorario)
		references Horarios (codigoHorario),
    constraint FK_Empleados_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion)    
);


-- Tipo Cliente
create table TipoCliente(
	codigoTipoCliente int not null auto_increment,
    descripcion varchar(45) not null,
	primary key PK_codigoTipoCliente (codigoTipoCliente)
);


-- Locales
create table Locales(
	codigoLocal int not null auto_increment,
    saldoFavor double(11,2) default 0.0,
    saldoContra double(11,2) default 0.0,
    mesesPendientes int default 0,
    disponibilidad boolean not null,
    valorLocal double(11,2) not null,
    valorAdministracion double(11,2) not null,
    primary key PK_codigoLocal (codigoLocal)
);


-- Clientes
 create table Clientes (
	codigoCliente int not null auto_increment,
    nombresCliente varchar (45) not null,
    apellidosCliente varchar (45) not null,
    telefonoCliente varchar(8) not null,
    direccionCliente varchar(60) not null,
    email varchar (45) not null,
    codigoLocal int not null,
    codigoAdministracion int not null,
    codigoTipoCliente int not null,
    primary key PK_codigoCliente (codigoCliente),
    constraint FK_Clientes_Locales foreign key (codigoLocal)
		references Locales (codigoLocal),
	constraint FK_Clientes_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion),
	constraint FK_Clientes_TipoCliente foreign key (codigoTipoCliente)
		references TipoCliente (codigoTipoCliente)
);


-- Cuentas por Cobrar
create table CuentasPorCobrar(
	codigoCuentaPorCobrar int auto_increment not null,
    numeroFactura varchar(45) not null,
    anio year(4) not null,
    mes int (2) not null,
    valorNetoPago double(11,2) not null,
    estadoPago varchar(45) not null,
    codigoAdministracion int not null,
    codigoCliente int not null,
    codigoLocal int not null,
    primary key PK_CuentasPorCobrar (codigoCuentaPorCobrar),
    constraint FK_CuentasPorCobrar_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion),
    constraint FK_CuentasPorCobrar_Clientes foreign key (codigoCliente)
		references Clientes (codigoCliente),
	constraint FK_CuentasPorCobrar_Locales foreign key (codigoLocal)
		references Locales (codigoLocal)  
);


-- Proveedores
create table Proveedores(
	codigoProveedor int auto_increment not null,
    NITProveedor varchar(45) not null,
    servicioPrestado varchar(45) not null,
    telefonoProveedor varchar(8) not null,
    direccionProveedor varchar(60) not null,
    saldoFavor double(11,2) not null,
    saldoContra double(11,2) not null,
    codigoAdministracion int not null,
    primary key PK_codigoProveedor (codigoProveedor),
    constraint FK_Proveedores_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion)
);


-- Cuentas por Pagar
create table CuentasPorPagar(
	codigoCuentaPorPagar int auto_increment not null,
    numeroFactura varchar(45) not null,
    fechaLimitePago date not null,
    estadoPago varchar(45) not null,
    valorNetoPago double(11,2) not null,
    codigoAdministracion int not null,
    codigoProveedor int not null,
    primary key PK_codigoCuentaPorPagar (codigoCuentaPorPagar),
    constraint FK_CuentasPorPagar_Administracion foreign key (codigoAdministracion)
		references Administracion (codigoAdministracion),
    constraint FK_CuentasPorPagar_Proveedores foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
);


-- Usuarios
create table Usuarios(
	codigoUsuario int not null auto_increment,
    nombreUsuario varchar(100) not null,
    apellidoUsuario varchar(100) not null,
    usuarioLogin varchar(45) not null,
    contrasena varchar(50) not null,
    primary key PK_codigoUsuario (codigoUsuario)
);


-- Login
create table Login(
	usuarioMaster varchar(50) not null,
    passwordLogin varchar(50) not null,
    primary key PK_usuarioMaster (usuarioMaster)
);



/* PROCEDIMIENTOS ALMACENADOS */


-- ----------------------- A D M I N I S T R A C I O N ----------------------------------------

-- 	Procedimiento Agregar Dato Administracion
Delimiter $$
	Create procedure sp_AgregarDatoAdministracion(
    in direccion varchar(45),
    in telefono varchar(8))
    Begin
		insert into Administracion (direccion, telefono) 
			values (direccion, telefono);
    End $$
Delimiter ;


-- 	Procedimiento Buscar Dato Administracion
Delimiter $$
	create procedure sp_BuscarDatoAdministracion (in codAdministracion int)
		Begin
			Select
				A.codigoAdministracion,
                A.direccion,
				A.telefono
                from Administracion A
                where A.codigoAdministracion = codAdministracion;
		End $$ 
Delimiter ;


-- 	Procedimiento Listar Datos Administracion
Delimiter $$
	create procedure sp_ListarDatosAdministracion ()
		Begin
			Select
				A.codigoAdministracion,
                A.direccion,
				A.telefono
                from Administracion A;
		End $$ 
Delimiter ;


-- 	Procedimiento Eliminar Dato Administracion
Delimiter $$
	create procedure sp_EliminarDatoAdministracion(in codAdministracion int)
		begin
			delete
				from Administracion 
					where codigoAdministracion = codAdministracion;
        end $$
Delimiter ;


-- 	Procedimiento Editar Dato Administracion
Delimiter $$
	create procedure sp_EditarDatoAdministracion(
    in codigoAdmin int,
    in direccionActualizado varchar(45), 
    in telefonoActualizado varchar(8))
		Begin
			update Administracion A
				 set
						A.direccion = direccionActualizado,
                        A.telefono = telefonoActualizado
							where
							A.codigoAdministracion = codigoAdmin;		
        End $$
Delimiter ;
desc Administracion



-- ----------------------- P R O V E E D O R E S ----------------------------------------

-- 	Procedimiento Agregar Proveedor
Delimiter $$
	Create procedure sp_AgregarProveedor(
    in NITProveedor varchar(45),
    in servicioPrestado varchar(45),
    in telefonoProveedor varchar(8),
    in direccionProveedor varchar(60),
    in saldoFavor double(11,2),
    in saldoContra double(11,2),
    in codigoAdministracion int)
    Begin
		insert into Proveedores 
			(NITProveedor, servicioPrestado, telefonoProveedor, direccionProveedor, 
				saldoFavor, saldoContra, codigoAdministracion) 
				values (NITProveedor, servicioPrestado, telefonoProveedor, direccionProveedor, 
						saldoFavor, saldoContra, codigoAdministracion);
    End $$
Delimiter ;


-- 	Procedimiento Buscar Proveedor
Delimiter $$
	create procedure sp_BuscarProveedor (in codProveedor int)
		Begin
			Select
				P.codigoProveedor,
				P.NITProveedor,
                P.servicioPrestado,
				P.telefonoProveedor,
                P.direccionProveedor,
                P.saldoFavor,
                P.saldoContra,
                P.codigoAdministracion
                from Proveedores P
                where P.codigoProveedor = codProveedor;
		End $$ 
Delimiter ;


-- 	Procedimiento Listar Proveedores
Delimiter $$
	create procedure sp_ListarProveedores()
		Begin
			Select
				P.codigoProveedor,
				P.NITProveedor,
                P.servicioPrestado,
				P.telefonoProveedor,
                P.direccionProveedor,
                P.saldoFavor,
                P.saldoContra,
                P.codigoAdministracion
                from Proveedores P;
		End $$ 
Delimiter ;


-- 	Procedimiento Eliminar Proveedor
Delimiter $$
	create procedure sp_EliminarProveedor(in codProveedor int)
		begin
			delete
				from Proveedores 
					where codigoProveedor = codProveedor;
        end $$
Delimiter ;


-- 	Procedimiento Editar Proveedor
Delimiter $$
	create procedure sp_EditarProveedor(
    in codProveedor int,
    in NITProveedorActualizado varchar(45), 
    in servicioPrestadoActualizado varchar(45),
    in telefonoProveedorActualizado varchar(8),
    in direccionProveedorActualizado varchar(60),
    in saldoFavorActualizado double(11,2),
    in saldoContraActualizado double(11,2))
		Begin
			update Proveedores P
				 set
						P.NITProveedor = NITProveedorActualizado,
						P.servicioPrestado = servicioPrestadoActualizado,
						P.telefonoProveedor = telefonoProveedorActualizado,
						P.direccionProveedor = direccionProveedorActualizado,
						P.saldoFavor = saldoFavorActualizado,
						P.saldoContra = saldoContraActualizado
							where
							P.codigoProveedor = codProveedor;			
        End $$
Delimiter ;



-- ----------------------- C U E N T A S  P O R  P A G A R ----------------------------------------

-- 	Procedimiento Agregar Cuenta Por Pagar
Delimiter $$
	create procedure sp_AgregarCuentaPorPagar(
    in numeroFactura varchar(45),
    in fechaLimitePago date,
    in estadoPago varchar(45),
    in valorNetoPago double(11,2),
    in codigoAdministracion int,
    in codigoProveedor int)
    Begin
		insert into CuentasPorPagar 
			(numeroFactura, fechaLimitePago, estadoPago, valorNetoPago, 
				codigoAdministracion, codigoProveedor) 
					values (numeroFactura, fechaLimitePago, estadoPago, valorNetoPago, 
						codigoAdministracion, codigoProveedor);
    End $$
Delimiter ;


-- 	Procedimiento Buscar Cuenta Por Pagar
Delimiter $$
	create procedure sp_BuscarCuentaPorPagar (in codCuentaPorPagar int)
		Begin
			Select
				CP.codigoCuentaPorPagar,
				CP.numeroFactura,
                CP.fechaLimitePago,
				CP.estadoPago,
                CP.valorNetoPago,
                CP.codigoAdministracion,
                CP.codigoProveedor
                from CuentasPorPagar CP
                where CP.codigoCuentaPorPagar = codCuentaPorPagar;
		End $$ 
Delimiter ;


-- 	Procedimiento Listar Cuentas Por Pagar
Delimiter $$
	create procedure sp_ListarCuentasPorPagar()
		Begin
			Select
				CP.codigoCuentaPorPagar,
				CP.numeroFactura,
                CP.fechaLimitePago,
				CP.estadoPago,
                CP.valorNetoPago,
                CP.codigoAdministracion,
                CP.codigoProveedor
                from CuentasPorPagar CP;
		End $$ 
Delimiter ;


-- 	Procedimiento Eliminar Cuenta Por Pagar
Delimiter $$
	create procedure sp_EliminarCuentaPorPagar(in codCuentaPorPagar int)
		begin
			delete
				from CuentasPorPagar 
					where codigoCuentaPorPagar = codCuentaPorPagar;
        end $$
Delimiter ;


-- 	Procedimiento Editar Cuenta Por Pagar
Delimiter $$
	create procedure sp_EditarCuentaPorPagar(
    in codCuentaPorPagar int,
    in numeroFacturaActualizado varchar(45), 
    in fechaLimitePagoActualizado date,
    in estadoPagoActualizado varchar(45),
    in valorNetoPagoActualizado double(11,2))
		Begin
			update CuentasPorPagar CP
				 set
						CP.numeroFactura = numeroFacturaActualizado,
						CP.fechaLimitePago = fechaLimitePagoActualizado,
						CP.estadoPago = estadoPagoActualizado,
						CP.valorNetoPago = valorNetoPagoActualizado
							where
							CP.codigoCuentaPorPagar = codCuentaPorPagar;			
        End $$
Delimiter ;




-- ----------------------- L O C A L E S ----------------------------------------

-- 	Procedimiento Agregar Local
Delimiter $$
	create procedure sp_AgregarLocal(
    in disponibilidad boolean,
    in valorLocal double(11,2),
    in valorAdministracion double(11,2)
    )
    Begin
		insert into Locales 
				(disponibilidad, valorLocal, valorAdministracion) 
					values (disponibilidad, valorLocal, valorAdministracion);
    End $$
Delimiter ;


-- 	Procedimiento Buscar Local
Delimiter $$
	create procedure sp_BuscarLocal (in codLocal int)
		Begin
			Select
				L.codigoLocal,
				L.saldoFavor,
                L.saldoContra,
				L.mesesPendientes,
                L.disponibilidad,
                L.valorLocal,
                L.valorAdministracion
                from Locales L
                where L.codigoLocal = codLocal;
		End $$ 
Delimiter ;


-- 	Procedimiento Listar Locales
Delimiter $$
	create procedure sp_ListarLocales()
		Begin
			Select
				L.codigoLocal,
				L.saldoFavor,
                L.saldoContra,
				L.mesesPendientes,
                L.disponibilidad,
                L.valorLocal,
                L.valorAdministracion
                from Locales L;
		End $$ 
Delimiter ;


-- 	Procedimiento Eliminar Local
Delimiter $$
	create procedure sp_EliminarLocal(in codLocal int)
		begin
			delete
				from Locales 
					where codigoLocal = codLocal;
        end $$
Delimiter ;


-- 	Procedimiento Editar Local
Delimiter $$
	create procedure sp_EditarLocal(
    in codLocal int,
	in saldoFavorActualizado double(11,2),
    in saldoContraActualizado double(11,2),
    in mesesPendientesActualizado int,
    in disponibilidadActualizado boolean,
    in valorLocalActualizado double(11,2),
    in valorAdministracionActualizado double(11,2))
		Begin
			update 	Locales L
				 set
						L.saldoFavor = saldoFavorActualizado,
						L.saldoContra = saldoContraActualizado,
						L.mesesPendientes = mesesPendientesActualizado,
						L.disponibilidad = disponibilidadActualizado,
						L.valorLocal = valorLocalActualizado,
						L.valorAdministracion = valorAdministracionActualizado
							where
							L.codigoLocal = codLocal;			
        End $$
Delimiter ;




-- ----------------------- T I P O  C L I E N T E ----------------------------------------

-- 	Procedimiento Agregar TipoCliente
Delimiter $$
	Create procedure sp_AgregarTipoCliente(
    in descripcion varchar(45)
    )
    Begin
		insert into TipoCliente 
			(descripcion) 
					values (descripcion);
    End $$
Delimiter ;


-- 	Procedimiento Buscar TipoCliente
Delimiter $$
	create procedure sp_BuscarTipoCliente (in codTipoCliente int)
		Begin
			Select
				TP.codigoTipoCliente,
				TP.descripcion
                from TipoCliente TP
                where TP.codigoTipoCliente = codTipoCliente;
		End $$ 
Delimiter ;


-- 	Procedimiento Listar TiposClientes
Delimiter $$
	create procedure sp_ListarTiposClientes()
		Begin
			Select
				TP.codigoTipoCliente,
                TP.descripcion
                from TipoCliente TP;
		End $$ 
Delimiter ;


-- 	Procedimiento Eliminar TipoCliente
Delimiter $$
	create procedure sp_EliminarTipoCliente(in codTipoCliente int)
		begin
			delete
				from TipoCliente 
					where codigoTipoCliente = codTipoCliente;
        end $$
Delimiter ;


-- 	Procedimiento Editar TipoCliente
Delimiter $$
	create procedure sp_EditarTipoCliente(
    in codTipoCliente int,
	in descripcionActualizado varchar(45))
		Begin
			update 	TipoCliente TP
				 set
						TP.descripcion = descripcionActualizado
							where
							TP.codigoTipoCliente = codTipoCliente;			
        End $$
Delimiter ;




-- ----------------------- C L I E N T E S ----------------------------------------

-- 	Procedimiento Agregar Cliente
Delimiter $$
	Create procedure sp_AgregarCliente(
    in nombresCliente varchar(45),
    in apellidosCliente varchar(45),
    in telefonoCliente varchar(8),
    in direccionCliente varchar(60),
    in email varchar(45),
    in codigoLocal int,
    in codigoAdministracion int,
    in codigoTipoCliente int
    )
    Begin
		insert into Clientes 
			(nombresCliente, apellidosCliente, telefonoCliente, direccionCliente, email,
				codigoLocal, codigoAdministracion, codigoTipoCliente) 
					values (nombresCliente, apellidosCliente, telefonoCliente, direccionCliente, email,
								codigoLocal, codigoAdministracion, codigoTipoCliente);
    End $$
Delimiter ;


-- 	Procedimiento Buscar Cliente
Delimiter $$
	create procedure sp_BuscarCliente (in codCliente int)
		Begin
			Select
				C.codigoCliente,
				C.nombresCliente,
                C.apellidosCliente,
                C.telefonoCliente,
                C.direccionCliente,
                C.email,
                C.codigoLocal,
                C.codigoAdministracion,
                C.codigoTipoCliente
                from Clientes C
                where C.codigoCliente = codCliente;
		End $$ 
Delimiter ;


-- 	Procedimiento Listar Clientes
Delimiter $$
	create procedure sp_ListarClientes()
		Begin
			Select
				C.codigoCliente,
				C.nombresCliente,
                C.apellidosCliente,
                C.telefonoCliente,
                C.direccionCliente,
                C.email,
                C.codigoLocal,
                C.codigoAdministracion,
                C.codigoTipoCliente
                from Clientes C;
		End $$ 
Delimiter ;


-- 	Procedimiento Eliminar Cliente
Delimiter $$
	create procedure sp_EliminarCliente(in codCliente int)
		begin
			delete
				from Clientes 
					where codigoCliente = codCliente;
        end $$
Delimiter ;


-- 	Procedimiento Editar Cliente
Delimiter $$
	create procedure sp_EditarCliente(
    in codCliente int,
	in nombresClienteActualizado varchar(45),
    in apellidosClienteActualizado varchar(45),
    in telefonoClienteActualizado varchar(8),
    in direccionClienteActualizado varchar(60),
    in emailActualizado varchar(45))
		Begin
			update 	Clientes C
				 set
						C.codigoCliente = codCliente,
						C.nombresCliente = nombresClienteActualizado,
						C.apellidosCliente = apellidosClienteActualizado,
						C.telefonoCliente = telefonoClienteActualizado,
						C.direccionCliente = direccionClienteActualizado,
						C.email = emailActualizado
							where
							C.codigoCliente = codCliente;			
        End $$
Delimiter ;




-- ----------------------- H O R A R I O S ----------------------------------------

-- 	Procedimiento Agregar Horario
Delimiter $$
	Create procedure sp_AgregarHorario(
    in horaEntrada varchar(10),
    in horaSalida varchar(10),
    in lunes boolean,
    in martes boolean,
    in miercoles boolean,
    in jueves boolean,
    in viernes boolean
    )
    Begin
		insert into Horarios
			(horaEntrada, horaSalida, lunes, martes, miercoles,
				jueves, viernes) 
					values (horaEntrada, horaSalida, lunes, martes, miercoles,
								jueves, viernes);
    End $$
Delimiter ;


-- 	Procedimiento Buscar Horario
Delimiter $$
	create procedure sp_BuscarHorario (in codHorario int)
		Begin
			Select
				H.codigoHorario,
                H.horaEntrada,
				H.horaSalida,
				H.lunes,
				H.martes,
				H.miercoles,
				H.jueves,
                H.viernes
                from Horarios H
                where H.codigoHorario = codHorario;
		End $$ 
Delimiter ;


-- 	Procedimiento Listar Horarios
Delimiter $$
	create procedure sp_ListarHorarios()
		Begin
			Select
				H.codigoHorario,
                H.horaEntrada,
				H.horaSalida,
				H.lunes,
				H.martes,
				H.miercoles,
				H.jueves,
                H.viernes
                from Horarios H;
		End $$ 
Delimiter ;


-- 	Procedimiento Eliminar Horario
Delimiter $$
	create procedure sp_EliminarHorario(in codHorario int)
		begin
			delete
				from Horarios 
					where codigoHorario = codHorario;
        end $$
Delimiter ;


-- 	Procedimiento Editar Horario
Delimiter $$
	create procedure sp_EditarHorario(
    in codHorario int,
	in horaEntradaActualizado varchar(10),
    in horaSalidaActualizado varchar(10),
    in lunesActualizado boolean,
    in martesActualizado boolean,
    in miercolesActualizado boolean,
    in juevesActualizado boolean,
    in viernesActualizado boolean
    )
		Begin
			update 	Horarios H
				 set
						H.codigoHorario = codHorario,
						H.horaEntrada = horaEntradaActualizado,
						H.horaSalida = horaSalidaActualizado,
						H.lunes = lunesActualizado,
						H.martes = martesActualizado,
						H.miercoles = miercolesActualizado,
						H.jueves = juevesActualizado,
						H.viernes = viernesActualizado
							where
							H.codigoHorario = codHorario;			
        End $$
Delimiter ;




-- ----------------------- D E P A R T A M E N T O S ----------------------------------------

-- 	Procedimiento Agregar Departamento
Delimiter $$
	create procedure sp_AgregarDepartamento(
    in nombreDepartamento varchar(45)
    )
    Begin
		insert into Departamentos 
			(nombreDepartamento) 
					values (nombreDepartamento);
    End $$
Delimiter ;


-- 	Procedimiento Buscar Departamento
Delimiter $$
	create procedure sp_BuscarDepartamento(in codDepartamento int)
		Begin
			Select
				D.codigoDepartamento,
                D.nombreDepartamento
                from Departamentos D
                where D.codigoDepartamento = codDepartamento;
		End $$ 
Delimiter ;


-- 	Procedimiento Listar Departamentos
Delimiter $$
	create procedure sp_ListarDepartamentos()
		Begin
			Select
				D.codigoDepartamento,
                D.nombreDepartamento
                from Departamentos D;
		End $$ 
Delimiter ;


-- 	Procedimiento Eliminar Departamento
Delimiter $$
	create procedure sp_EliminarDepartamento(in codDepartamento int)
		begin
			delete
				from Departamentos 
					where codigoDepartamento = codDepartamento;
        end $$
Delimiter ;


-- 	Procedimiento Editar Departamento
Delimiter $$
	create procedure sp_EditarDepartamento(
    in codDepartamento int,
	in nombreDepartamentoActualizado varchar(45)
    )
		Begin
			update 	Departamentos D
				 set
						D.codigoDepartamento = codDepartamento,
						D.nombreDepartamento = nombreDepartamentoActualizado
							where
								D.codigoDepartamento = codDepartamento;		
        End $$
Delimiter ;




-- ----------------------- C A R G O S ----------------------------------------

-- 	Procedimiento Agregar Cargo
Delimiter $$
	create procedure sp_AgregarCargo(
    in nombreCargo varchar(45)
    )
    Begin
		insert into Cargos 
			(nombreCargo) 
					values (nombreCargo);
    End $$
Delimiter ;


-- 	Procedimiento Buscar Cargo
Delimiter $$
	create procedure sp_BuscarCargo(in codCargo int)
		Begin
			Select
				C.codigoCargo,
                C.nombreCargo
                from Cargos C
                where C.codigoCargo = codCargo;
		End $$ 
Delimiter ;


-- 	Procedimiento Listar Cargos
Delimiter $$
	create procedure sp_ListarCargos()
		Begin
			Select
				C.codigoCargo,
                C.nombreCargo
                from Cargos C;
		End $$ 
Delimiter ;


-- 	Procedimiento Eliminar Cargo
Delimiter $$
	create procedure sp_EliminarCargo(in codCargo int)
		begin
			delete
				from Cargos 
					where codigoCargo = codCargo;
        end $$
Delimiter ;


-- 	Procedimiento Editar Cargo
Delimiter $$
	create procedure sp_EditarCargo(
    in codCargo int,
	in nombreCargoActualizado varchar(45)
    )
		Begin
			update 	Cargos C
				 set
						C.codigoCargo = codCargo,
						C.nombreCargo = nombreCargoActualizado
							where
								C.codigoCargo = codCargo;		
        End $$
Delimiter ;



-- ----------------------- E M P L E A D O S ----------------------------------------

-- 	Procedimiento Agregar Empleado
Delimiter $$
	Create procedure sp_AgregarEmpleado(
    in nombresEmpleado varchar(45),
    in apellidosEmpleado varchar(45),
    in correoElectronico varchar(45),
    in telefonoEmpleado varchar(8),
    in fechaContratacion date,
    in sueldo double(11,2),
    in codigoDepartamento int,
    in codigoCargo int,
    in codigoHorario int,
    in codigoAdministracion int
    )
    Begin
		insert into Empleados 
			(nombresEmpleado, apellidosEmpleado, correoElectronico, telefonoEmpleado, fechaContratacion,
				sueldo, codigoDepartamento, codigoCargo, codigoHorario, codigoAdministracion) 
					values (nombresEmpleado, apellidosEmpleado, correoElectronico, telefonoEmpleado, fechaContratacion,
								sueldo, codigoDepartamento, codigoCargo, codigoHorario, codigoAdministracion);
    End $$
Delimiter ;

-- 	Procedimiento Buscar Empleado
Delimiter $$
	create procedure sp_BuscarEmpleado (in codEmpleado int)
		Begin
			Select
				E.codigoEmpleado,
				E.nombresEmpleado,
                E.apellidosEmpleado,
                E.correoElectronico,
                E.telefonoEmpleado,
                E.fechaContratacion,
                E.sueldo,
                E.codigoDepartamento,
                E.codigoCargo,
                E.codigoHorario,
                E.codigoAdministracion
                from Empleados E
                where E.codigoEmpleado = codEmpleado;
		End $$ 
Delimiter ;


-- 	Procedimiento Listar Empleados
Delimiter $$
	create procedure sp_ListarEmpleados()
		Begin
			Select
				E.codigoEmpleado,
				E.nombresEmpleado,
                E.apellidosEmpleado,
                E.correoElectronico,
                E.telefonoEmpleado,
                E.fechaContratacion,
                E.sueldo,
                E.codigoDepartamento,
                E.codigoCargo,
                E.codigoHorario,
                E.codigoAdministracion
                from Empleados E;
		End $$ 
Delimiter ;


-- 	Procedimiento Eliminar Empleado
Delimiter $$
	create procedure sp_EliminarEmpleado(in codEmpleado int)
		begin
			delete
				from Empleados 
					where codigoEmpleado = codEmpleado;
        end $$
Delimiter ;


-- 	Procedimiento Editar Empleado
Delimiter $$
	create procedure sp_EditarEmpleado(
    in codEmpleado int,
	in nombresEmpleadoActualizado varchar(45),
    in apellidosEmpleadoActualizado varchar(45),
    in correoElectronicoActualizado varchar(45),
    in telefonoEmpleadoActualizado varchar(8),
    in fechaContratacionActualizado date,
    in sueldoActualizado double(11,2))
		Begin
			update 	Empleados E
				 set
						E.codigoEmpleado = codEmpleado,
						E.nombresEmpleado = nombresEmpleadoActualizado,
						E.apellidosEmpleado = apellidosEmpleadoActualizado,
						E.correoElectronico = correoElectronicoActualizado,
						E.telefonoEmpleado = telefonoEmpleadoActualizado,
						E.fechaContratacion = fechaContratacionActualizado,
						E.sueldo = sueldoActualizado
							where
							E.codigoEmpleado = codEmpleado;			
        End $$
Delimiter ;



-- ----------------------- C U E N T A S  P O R  C O B R A R ----------------------------------------

-- 	Procedimiento Agregar Cuenta Por Cobrar
Delimiter $$
	create procedure sp_AgregarCuentaPorCobrar(
    in numeroFactura varchar(45),
    in anio year(4),
    in mes int(2),
    in valorNetoPago double(11,2),
    in estadoPago varchar(45),
    in codigoAdministracion int,
    in codigoCliente int,
    in codigoLocal int
    )
    Begin
		insert into CuentasPorCobrar 
			(numeroFactura, anio, mes, valorNetoPago, estadoPago,
				codigoAdministracion, codigoCliente, codigoLocal) 
					values (numeroFactura, anio, mes, valorNetoPago, estadoPago,
						codigoAdministracion, codigoCliente, codigoLocal);
    End $$
Delimiter ;


-- 	Procedimiento Buscar Cuenta Por Cobrar
Delimiter $$
	create procedure sp_BuscarCuentaPorCobrar(in codCuentaPorCobrar int)
		Begin
			Select
				CC.codigoCuentaPorCobrar,
				CC.numeroFactura,
                CC.anio,
				CC.mes,
                CC.valorNetoPago,
                CC.estadoPago,
                CC.codigoAdministracion,
                CC.codigoCliente,
                CC.codigoLocal
                from CuentasPorCobrar CC
                where CC.codigoCuentaPorCobrar = codCuentaPorCobrar;
		End $$ 
Delimiter ;


-- 	Procedimiento Listar Cuentas Por Cobrar
Delimiter $$
	create procedure sp_ListarCuentasPorCobrar()
		Begin
			Select
				CC.codigoCuentaPorCobrar,
				CC.numeroFactura,
                CC.anio,
				CC.mes,
                CC.valorNetoPago,
                CC.estadoPago,
                CC.codigoAdministracion,
                CC.codigoCliente,
                CC.codigoLocal
                from CuentasPorCobrar CC;
		End $$ 
Delimiter ;


-- 	Procedimiento Eliminar Cuenta Por Cobrar
Delimiter $$
	create procedure sp_EliminarCuentaPorCobrar(in codCuentaPorCobrar int)
		begin
			delete
				from CuentasPorCobrar 
					where codigoCuentaPorCobrar = codCuentaPorCobrar;
        end $$
Delimiter ;


-- 	Procedimiento Editar Cuenta Por Cobrar
Delimiter $$
	create procedure sp_EditarCuentaPorCobrar(
    in codCuentaPorCobrar int,
    in numeroFacturaActualizado varchar(45), 
    in anioActualizado year(4),
    in mesActualizado int(2),
    in valorNetoPagoActualizado double(11,2),
    in estadoPagoActualizado varchar(45))
		Begin
			update CuentasPorCobrar CC
				 set
						CC.numeroFactura = numeroFacturaActualizado,
						CC.anio = anioActualizado,
						CC.mes = mesActualizado,
						CC.valorNetoPago = valorNetoPagoActualizado,
						CC.estadoPago = estadoPagoActualizado
							where
							CC.codigoCuentaPorCobrar = codCuentaPorCobrar;			
        End $$
Delimiter ;



-- ----------------------- U S U A R I O S ----------------------------------------

-- Procedimiento Agregar Usuario
Delimiter $$
	create procedure sp_AgregarUsuario(
    in nombreUsuario varchar(100),
    in apellidoUsuario varchar(100),
    in usuarioLogin varchar(50),
    in contrasena varchar(50)
    )
    Begin 
		insert into Usuarios (nombreUsuario, apellidoUsuario, usuarioLogin, contrasena)
			values (nombreUsuario, apellidoUsuario, usuarioLogin, contrasena);
	End$$
Delimiter ;


-- Procedimiento Listar Usuarios
Delimiter $$
	create procedure sp_ListarUsuarios()
		Begin
			Select
				U.codigoUsuario,
				U.nombreUsuario,
				U.apellidoUsuario,
                U.usuarioLogin,
				U.contrasena
                from Usuarios U;
		End $$ 
Delimiter ;



-- ----------------------- A G R E G A R   D A T O S   A   L A S   E N T I D A D E S ----------------------------------------

-- ADMINISTRACIÓN --
call sp_AgregarDatoAdministracion('Zona 01, Guatemala', '58474618');
call sp_AgregarDatoAdministracion('Zona 02, Guatemala', '41988293');
call sp_AgregarDatoAdministracion('Zona 21, Guatemala', '35410222');
call sp_AgregarDatoAdministracion('Zona 06, Mixco', '22073352');
call sp_AgregarDatoAdministracion('Zona 18, Villa Nueva', '56083554');
call sp_AgregarDatoAdministracion('Zona 07, Guatemala', '34034025');

-- PROVEEDORES
call sp_AgregarProveedor('45321236-1','Alimentación','45321201','Zona 01, Guatemala',750.36,169.56,1);
call sp_AgregarProveedor('94523125-2','Transporte','55998741','Zona 02, Villa Nueva',8536.50,755.89,2);

-- CUENTAS POR PAGAR
call sp_AgregarCuentaPorPagar('987654','2021-09-17','Cancelado',7500.36,1,1);
call sp_AgregarCuentaPorPagar('742536','2021-07-21','Pendiente',2570.55,2,2);

-- LOCALES
call sp_AgregarLocal(true,5800.25,1500.36);
call sp_AgregarLocal(false,7500.55,2500.55);
call sp_AgregarLocal(true,11250.25,3500.36);
call sp_AgregarLocal(false,25000.12,1850.35);
call sp_AgregarLocal(true,9745.20,2351.36);
call sp_AgregarLocal(false,8654.25,2500.55);

-- TIPO CLIENTE
call sp_AgregarTipoCliente('Nuevo');
call sp_AgregarTipoCliente('Frecuente');

-- CLIENTES
call sp_AgregarCliente('Rodrigo Gerardo','Cárdenas Monroy','58018517','Zona 01, Guatemala','rcardenas@kinal.edu.gt',1,1,1);
call sp_AgregarCliente('Jose Humberto','Reyes Montenegro','34048852','Zona 11, Mixco','jhumbertoreyes@gmail.com',2,2,2);
call sp_AgregarCliente('Norman Emilio','Pérez Sosa','54012535','Zona 02, Guatemala','normanemilio@gmail.com',3,3,1);
call sp_AgregarCliente('Francisco Jose','Chic Barrios','56122525','Zona 07, Escuintla','fjose123@yahoo.com',4,4,2);
call sp_AgregarCliente('Gerson Eduardo','Hernández Tu','56122525','Zona 11, Petén','gersonh@outlook.com',5,5,2);
call sp_AgregarCliente('Ana María','Barillas Colindres','34012575','Zona 07, Guatemala','anamaria@yahoo.com',6,6,1);

-- HORARIOS
call sp_AgregarHorario('12:45','20:45', true, false, true, false, true);
call sp_AgregarHorario('14:45','22:45', false, true, false, true, false);

-- DEPARTAMENTOS
call sp_AgregarDepartamento('Comercial');
call sp_AgregarDepartamento('Contabilidad');
call sp_AgregarDepartamento('Dirección General');
call sp_AgregarDepartamento('Informática');
call sp_AgregarDepartamento('Producción');
call sp_AgregarDepartamento('Recursos Humanos');
call sp_AgregarDepartamento('Operaciones');
call sp_AgregarDepartamento('Servicios');

-- CARGOS
call sp_AgregarCargo('Community Manager');
call sp_AgregarCargo('Vendedor');
call sp_AgregarCargo('Gerente Administrativo');
call sp_AgregarCargo('Asistente de Impuestos');
call sp_AgregarCargo('Gerente General');
call sp_AgregarCargo('Recepcionista');
call sp_AgregarCargo('Programador Senior');
call sp_AgregarCargo('Desarrollador Web');
call sp_AgregarCargo('Técnico en Control de Calidad');
call sp_AgregarCargo('Jefe de Producción');
call sp_AgregarCargo('Gerente de Recursos Humanos');
call sp_AgregarCargo('Encargado de Reclutamiento');
call sp_AgregarCargo('Jefe de Logística');
call sp_AgregarCargo('Operador de Montacargas');
call sp_AgregarCargo('Gerente de Servicios');
call sp_AgregarCargo('Chofer');

-- EMPLEADOS
call sp_AgregarEmpleado('Susan Guadalupe','Reyes Montenegro','sunlup@outlook.com','34088056','2015-11-07',3500.29,1,1,2,1);
call sp_AgregarEmpleado('Norma Crisanta','Montenegro Guillermo','normac@gmail.com','35851245','2010-02-25',8000.99,2,3,1,2);
call sp_AgregarEmpleado('Sandra Nohemi','Monzon Gonzales','nohemigonza@gmail.com','54201257','2009-03-20',8000.99,3,5,1,3);
call sp_AgregarEmpleado('Esteban Jose','Reyes Rax','estebnrax@gmail.com','35721202','2018-10-19',7500.99,4,7,2,4);
call sp_AgregarEmpleado('Deiry Nayeli','Zacarías Gomez','nayeli25@gmail.com','44542510','2020-11-07',9500.99,5,9,1,5);
call sp_AgregarEmpleado('Angeles Alessandra','Reyes Lopez','aareyeslopez@outlook.com','38741210','2017-02-02',4500.99,6,11,2,6);
call sp_AgregarEmpleado('Rolsin Gabriel','de León Luna','rdeleon@gmail.com','58855225','2012-01-31',17500.99,7,13,1,1);
call sp_AgregarEmpleado('Miguel Ottoniel','Gonzales Perén','ottonielmperen@gmail.com','35297412','2013-04-22',3750.99,8,15,2,2);

-- CUENTAS POR COBRAR
call sp_AgregarCuentaPorCobrar('412358',2021,05,5802.35,'Pendiente',1,1,1);
call sp_AgregarCuentaPorCobrar('745212',2021,08,10750.25,'Cancelado',2,2,2);
call sp_AgregarCuentaPorCobrar('562012',2021,07,8500.25,'Cancelado',3,3,3);
call sp_AgregarCuentaPorCobrar('856422',2021,02,4578.36,'Pendiente',4,4,4);
call sp_AgregarCuentaPorCobrar('956135',2021,11,2574.35,'Pendiente',5,5,5);
call sp_AgregarCuentaPorCobrar('521257',2021,12,25841.10,'Cancelado',6,6,6);
call sp_AgregarCuentaPorCobrar('853214',2021,10,45010.35,'Cancelado',5,1,4);

-- USUARIOS
call sp_AgregarUsuario('Rodrigo','Cárdenas','rcardenas','12345');




-- ----------------------- L I S T A R    D A T O S ----------------------------------------

call sp_ListarDatosAdministracion();
call sp_ListarProveedores();
call sp_ListarCuentasPorPagar();
call sp_ListarLocales();
call sp_ListarTiposClientes();
call sp_ListarClientes();
call sp_ListarHorarios();
call sp_ListarDepartamentos();
call sp_ListarCargos();
call sp_ListarEmpleados();
call sp_ListarCuentasPorCobrar();
call sp_ListarCargos();
call sp_ListarUsuarios();



-- ----------------------- A C T I V I D A D E S   D E   C L A S E ----------------------------------------

/*
-- Rodrigo Gerardo Cárdenas Monroy - 2020166
-- 17/06/2021

-- ACTIVIDAD # 1
-- Agregar LOCALES

call sp_AgregarLocal(true,2500.00,1800.50);
call sp_AgregarLocal(false,3650.36,2750.35);
call sp_AgregarLocal(true,5800.00,4800.00);
call sp_AgregarLocal(true,9000.00,8500.00);
call sp_AgregarLocal(true,5050.10,4750.00);
call sp_AgregarLocal(false,7500.00,7412.00);
call sp_AgregarLocal(true,2500.25,1745.52);
call sp_AgregarLocal(false,6000.35,5823.25);
call sp_AgregarLocal(false,7500.25,7412.33);
call sp_AgregarLocal(true,4750.39,4532.25);

call sp_ListarLocales();


-- ACTIVIDAD # 2
-- Calcular Líquido de LOCALES

Delimiter $$
	create procedure sp_CalcularLiquido(in codLocal int)
	Begin
		select(L.saldoFavor - L.saldoContra) as Liquido
        from Locales L
        where L.codigoLocal = codLocal;
    End$$
Delimiter ;
call sp_CalcularLiquido(1);
*/



/*
-- Rodrigo Gerardo Cárdenas Monroy - 2020166
-- 07/07/2021

-- ACTIVIDAD # 1
-- Agregar PROVEEDORES

call sp_AgregarProveedor('55998877-1','Alimentación','35445577','Zona 01, Guatemala',556.36,169.56,1);
call sp_AgregarProveedor('45364123-2','Transporte','56321124','Zona 02, Chimaltenango',8503.5,756.35,2);
call sp_AgregarProveedor('95123621-3','Limpieza','63212352','Zona 03, Zacapa',3556.36,877.25,1);
call sp_AgregarProveedor('53212524-4','Seguridad','35651236','Zona 04, Guatemala',1050.85,450.23,2);
call sp_AgregarProveedor('36214742-5','Internet y Cable','56083437','Zona 05, Mixco',1500.05,178.24,1);
call sp_AgregarProveedor('53621258-6','Maquinaria','34083402','Zona 06, Villa Nueva',2365.26,180.23,2);
call sp_AgregarProveedor('25364212-7','Seguridad Médica','35741232','Zona 07, Santa Rosa',2503.64,255.56,1);
call sp_AgregarProveedor('89512351-8','Agua y Luz','41988233','Zona 08, Flores Petén',950.89,260.56,2);
call sp_AgregarProveedor('36871123-9','Plomería','47203636','Zona 09, Guatemala',1500.65,1400.56,1);
call sp_AgregarProveedor('78521364-1','Carpintería','58963212','Zona 10, Guatemala',1100.46,900.56,2);
call sp_ListarProveedores();


-- ACTIVIDAD # 2
-- Calcular Líquido de un PROVEEDOR
   
Delimiter $$
	create procedure sp_CalcularLiquidoProveedor(in codProveedor int)
	Begin
		select
        P.codigoProveedor,
		P.NITProveedor,
		P.servicioPrestado,
		P.telefonoProveedor,
		P.direccionProveedor,
		P.saldoFavor,
		P.saldoContra,
		P.codigoAdministracion,
        (P.saldoFavor - P.saldoContra) as saldoLiquidoProveedor
        from Proveedores P
        where P.codigoProveedor = codProveedor;
    End$$
Delimiter ;

call sp_CalcularLiquidoProveedor(2);  


-- ACTIVIDAD # 3
-- Saldo Líquido de todos los PROVEEDORES

Delimiter $$
	create procedure sp_CalcularLiquidoProveedores()
	Begin
		select
        P.codigoProveedor,
		P.NITProveedor,
		P.servicioPrestado,
		P.telefonoProveedor,
		P.direccionProveedor,
		P.saldoFavor,
		P.saldoContra,
		P.codigoAdministracion,
        (P.saldoFavor - P.saldoContra) as saldoLiquidoProveedor
        from Proveedores P;
    End$$
Delimiter ;

call sp_CalcularLiquidoProveedores();  
*/


/*
-- Rodrigo Gerardo Cárdenas Monroy - 2020166
-- 08/07/2021

-- Actividad # 1
-- Agregar LOCALES

call sp_AgregarLocal(5860.36,2500.25,4,true,2500.00,1800.50);
call sp_AgregarLocal(9850.34,2541.30,9,false,3650.36,2750.35);
call sp_AgregarLocal(1600,400.32,10,true,5800.00,4800.00);
call sp_AgregarLocal(5800.36,2000.36,11,true,9000.00,8500.00);
call sp_AgregarLocal(7600.25,1750.31,2,true,5050.10,4750.00);
call sp_AgregarLocal(6500,4500,5,false,7500.00,7412.00);
call sp_AgregarLocal(1782,350,2,true,2500.25,1745.52);
call sp_AgregarLocal(14756.36,1975.36,3,false,6000.35,5823.25);
call sp_AgregarLocal(7500.74,2510.36,1,true,7500.25,7412.33);
call sp_AgregarLocal(10500.30,1000.50,5,false,4750.39,4532.25);


-- Actividad # 2
-- Calcular Deuda de Meses Pendientes de un LOCAL

Delimiter $$
	create procedure sp_CalcularDeudaMesPendiente(in codLocal int)
	Begin
		select
		L.codigoLocal,
		L.saldoFavor,
		L.saldoContra,
		L.mesesPendientes,
		L.valorLocal,
        (L.valorLocal * L.mesesPendientes) as valorMesesPendientes,
        (L.saldoFavor-L.saldoContra) as saldoLiquido,
        ((L.valorLocal * L.mesesPendientes)-(L.saldoFavor-L.saldoContra)) as valorDeudaLocal
        from Locales L
        where L.codigoLocal = codLocal;
    End$$
Delimiter ;

call sp_CalcularDeudaMesPendiente(7); 


-- Actividad # 3
-- Calcular Deuda de Meses Pendientes de todos los LOCALES

Delimiter $$
	create procedure sp_CalcularDeudaMesesPendientes()
	Begin
		select
		L.codigoLocal,
		L.saldoFavor,
		L.saldoContra,
		L.mesesPendientes,
		L.valorLocal,
        (L.valorLocal * L.mesesPendientes) as valorMesesPendientes,
        (L.saldoFavor-L.saldoContra) as saldoLiquido,
        ((L.valorLocal * L.mesesPendientes)-(L.saldoFavor-L.saldoContra)) as valorDeudaLocal
        from Locales L;
    End$$
Delimiter ;

call sp_CalcularDeudaMesesPendientes(); 


-- Actividad # 4
-- Calcular Cantidad de LOCALES
-- Calcular cantidad de LOCALES DISPONIBLES
-- Calcular cantidad de LOCALES NO DISPONIBLES

Delimiter $$
	create procedure sp_DatosDeLocales()
	Begin
		select count(*) as cantidadLocales from Locales L;
        select count(*) as localesDisponibles from Locales L where L.disponibilidad is true;
        select count(*) as localesNoDisponibles from Locales L where L.disponibilidad is false;
    End$$
Delimiter ;

call sp_DatosDeLocales(); 
*/



-- PRIVILEGIOS ADMINISTRADOR IREPORT

ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admin'; 
