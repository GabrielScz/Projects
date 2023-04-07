USE optiqalumnos;

DROP PROCEDURE IF EXISTS generarTokens;
DELIMITER $$
CREATE PROCEDURE generarTokens(    
                                    /* Datos de Usuario */
                                    IN	  var_idUsuario		  INT, 			  -- 1
                                    IN 	  var_lastToken 	  VARCHAR(65)     -- 2
                )      
    BEGIN   
        -- Actualizamos los datos del Usuario:
        UPDATE usuario
        SET lastToken = var_lastToken, dateLastToken = CURRENT_TIMESTAMP
        WHERE idUsuario = var_idUsuario;
END
$$
DELIMITER ;  

CALL generarTokens(1, 'sdaashjakfb2113masd2k');

