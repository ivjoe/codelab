package joni.jaxrs.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Jonatan Ivanov
 */
@Path("/")
public class EchoService {

	@GET
	@Path("/echo/{param}")
	@Produces(MediaType.TEXT_PLAIN)
	public String echo(@PathParam("param") String msg)
	{
		return "echo: " + msg;
	}

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello()
	{
		return "hello";
	}
}
