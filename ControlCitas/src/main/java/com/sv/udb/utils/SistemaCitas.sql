CREATE database GestionDeCitas;
use GestionDeCitas;

-- NOTA IMPORTANTE: si en algun momento del mantenimiento da error,
-- probar borrar el ON UPDATE CASCADE (creo q ya he experimentado ese error, pero no lo recuerdo)

CREATE TABLE Visitante(
codi_visi INT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- Identificador, codigo de registro
dui_visi varchar(10),
nomb_visi VARCHAR(40) NOT NULL, -- Nombre de Visitante
apel_visi VARCHAR(40) NOT NULL, -- Apellido de Visitante
usua_visi VARCHAR(40), -- Nombre de Usuario (necesario para ingresar al sistema)
pass_visi VARCHAR (200), -- Contraseña (ENCRIPTADA) de visitante
corr_visi VARCHAR(100) unique,	-- Correo del visitante (tambien puede ser usado para ingresar al sistema)
tipo_visi INT -- Sirve para identificar si es una persona particular o responsable de alumno
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE Alumno_visitante(
codi_alum_visi INT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- Identificador, codigo de registro
codi_visi INT NOT NULL,	-- Identificador, de visitante, llave foránea visitante
carn_alum VARCHAR(10) NOT NULL, -- Dato Tomado Desde Web Service llave foránea
pare_alum_visi INT NOT NULL, -- Parentesco entre el alumno y visitante (previamente predefinidos)
espe_alum_visi VARCHAR(80), -- Especificar un tipo de parentesco en caso de que no se encuentre predefinido
esta_alum_visi INT NOT NULL, -- Especificar si la relación sigue vigente o se ha dado de baja
FOREIGN KEY (codi_visi) REFERENCES Visitante (codi_visi) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE Evento(
codi_event INT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- Identificador, codigo de registro
codi_luga INT NOT NULL,
nomb_even VARCHAR(50) NOT NULL,
fecha__inic_even DATE NOT NULL,
fecha__fina_even DATE NOT NULL,
hora_inic_even varchar(8) NOT NULL,
hora_fina_even varchar(8) NOT NULL
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE Cita(
codi_cita INT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- Identificador, codigo de registro
codi_lugar INT, -- Dato Tomado Desde Web Service llave foránea
codi_even INT,
codi_usua INT, -- Dato Tomado Desde Web Service, llave foránea para el citador
tipo_cita INT NOT NULL, -- Identificar si es una cita solicitada (pre programada), o normal (no programada)
tipo_visi INT, -- Identificar si el visutante es particular, representante alumno, o grupo
tipo_dura INT, -- Identificar si la cita tiene una duración de "x" periodo de tiempo, o solamente un dia
desc_cita VARCHAR(500), -- Describir motivo de cita
esta_cita INT NOT NULL,-- Especificar si la cita sigue vigente o se ha dado de baja
nomb_grup_cita varchar(100),
cant_grup_cita int,
fech_lleg_cita date,
hora_lleg_cita varchar(8),
fech_sali_cita date,
hora_sali_cita varchar(8),
FOREIGN KEY (codi_even) REFERENCES Evento (codi_event) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE Cambio_cita(
codi_camb_cita INT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- Identificador, codigo de registro
codi_cita INT NOT NULL,	-- Llave foránea para la cita
fech_camb_cita DATE NOT NULL, -- Fecha de cuando se realizo el registro (cambio)
hora_camb_cita varchar(8) NOT NULL, -- Hora de cuando se realizo el registro (cambio)
fech_inic_cita_nuev DATE NOT NULL, -- Nueva fecha inicio registrada que se aplicará al registro (fecha cita)
hora_inic_cita_nuev varchar(8) NOT NULL, -- Nueva hora inicio registrada que se aplicará al registro (hora cita)
fech_fin_cita_nuev DATE NOT NULL, -- Nueva fecha fin registrada que se aplicará al registro (fecha cita)
hora_fin_cita_nuev varchar(8) NOT NULL, -- Nueva hora fin registrada que se aplicará al registro (hora cita)
moti_camb_cita VARCHAR(500), -- Descripción del motivo por que cual se realizo el cambio
esta_camb_cita INT NOT NULL, -- Estado del cambio aplicado a la cita (Solicitada, Confirmada, Cancelada, Reprogramada, Solicitada Reprogramación, Solicitada Cancelación, Finalizada)
FOREIGN KEY (codi_cita) REFERENCES Cita (codi_cita) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE Visitante_cita(
codi_visi_cita INT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- Identificador, codigo de registro
codi_visi INT NOT NULL, -- Llave foránea para de visitante
codi_cita INT NOT NULL, -- Llave foránea de cita
FOREIGN KEY (codi_visi) REFERENCES Visitante (codi_visi) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (codi_cita) REFERENCES Cita (codi_cita) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE Horario_disponible(
codi_hora_disp INT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- Identificador, codigo de registro
codi_usua INT NOT NULL, -- Llave foránea desde Web Service para usuario
dia_hora_disp VARCHAR(80) NOT NULL,-- Dia Disponible
hora_inic_hora_disp varchar(8) NOT NULL, -- Hora Inicio 
hora_fina_hora_disp varchar(8) NOT NULL, --  Hora Fin
anio_hora_disp char(4) NOT NULL, -- Año Disponible
esta_hora_disp INT NOT NULL -- Estado de horario
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE Excepcion_horario_disponible(
codi_exce_hora_disp INT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- Identificador, codigo de registro
codi_hora_disp INT NOT NULL,
fech_exce_hora_disp DATE NOT NULL,
razo_exce_hora_disp VARCHAR(100),
FOREIGN KEY (codi_hora_disp) REFERENCES Horario_disponible (codi_hora_disp) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;