package br.com.marmitaria.entity.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
	public Object data;
	public String message;
}
