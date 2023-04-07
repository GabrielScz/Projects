USE HelpDesk;

DROP PROCEDURE IF EXISTS generarUltimaConexion;
DELIMITER $$
CREATE PROCEDURE generarUltimaConexion(    
                                    /* Datos de Usuario */
                                    IN	  var_idUsuario		  INT 			  -- 1
                )      
    BEGIN   
        -- Actualizamos los datos del Usuario:
        UPDATE usuario
        SET ultimaConexion = CURRENT_TIMESTAMP
        WHERE idUsuario = var_idUsuario;
END
$$
DELIMITER ;  

CALL generarUltimaConexion(1);