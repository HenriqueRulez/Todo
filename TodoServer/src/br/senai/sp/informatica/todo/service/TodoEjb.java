package br.senai.sp.informatica.todo.service;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.senai.sp.informatica.todo.model.Item;
import br.senai.sp.informatica.todo.model.Todo;

@Path("/")
@Stateless
@LocalBean
public class TodoEjb {
	@PersistenceContext(name = "TodoServer")
	private EntityManager manager;
	private final String url = "http://192.168.2.250/TodoServer/";
	private Logger logger = Logger.getLogger(this.getClass().getName());

	// Versão
	@GET
	@Path("version")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response version() {
		return Response.ok("[{\"version\":Todo Service 1.0 - Test}]").status(200).build();
	}

	// Todos
	@GET
	@Path("lista/{feito}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getAll(@PathParam("feito") int feito) {
		return Response.ok(manager.createQuery("select t from Todo t where t.feito = :feito and t.del = false", Todo.class)
				.setParameter("feito", feito == 1)
				.getResultList()).status(200).build();
	}
	
	@GET
	@Path("lista")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getAll() {
		return Response.ok(manager.createQuery("select t from Todo t", Todo.class)
				.getResultList()).status(200).build();
	}

	// Paginação
	@GET
	@Path("{de}/{qtd}/{feito}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response get(@PathParam("de") long de,@PathParam("qtd")  int qtd, @PathParam("feito") int feito ) {
		return Response.ok(manager.createQuery("select t from Todo t where t.id > :de and t.feito = :feito", Todo.class)
				.setParameter("de", de)
				.setParameter("feito", feito == 1)
				.setMaxResults(qtd)
				.getResultList()).status(200).build();
	}

	// Quantidade
	@GET
	@Path("count")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response size() {
		return Response.ok("[{\"count\":"
				+ ((Long) manager.createQuery("select count(t) from Todo t").getSingleResult()).intValue()
				+ "}]").status(200).build();
	}

	// Quantidade Feito
	@GET
	@Path("feito")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response feito() {
		return Response.ok("[{\"count\":"
				+ ((Long) manager.createQuery("select count(t) from Todo t where t.feito = true").getSingleResult()).intValue()
				+ "}]").status(200).build();
	}

	// Quantidade Não Feito
	@GET
	@Path("afazer")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response naoFeito() {
		return Response.ok("[{\"count\":"
				+ ((Long) manager.createQuery("select count(t) from Todo t where t.feito = false").getSingleResult()).intValue()
				+ "}]").status(200).build();
	}

	// Por ID
	@GET
	@Path("todo/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
	public Response getById(@PathParam("id") long id) {
		return Response.ok(manager.find(Todo.class, id)).status(200).build();
	}
	
	// Inclui Todo
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTodo(Todo obj) {
		try {
			List<Item> itens = obj.getItens();
			List<Item> itensGravados = new ArrayList<>();
			
			for (int i = 0; i < itens.size(); i++) {
				Item item = itens.get(i);
				item = manager.merge(item);
				itensGravados.add(i, item);
			}
			obj.setItens(itensGravados);
			
			Todo todo = manager.merge(obj);
			
			URI uri = new URL(url + "todo/" + todo.getId()).toURI();

			return Response.ok(todo).status(201).location(uri).build();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			return Response.status(500).build();
		}
	}
	
	// Inclui Item
	@POST
	@Path("todo/{idTodo}/item/{idItem}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addItem(@PathParam("idTodo") long idTodo, Item obj) {
		try {
			Todo todo = manager.find(Todo.class, idTodo);
			Item item = obj;
			List<Item> itens = todo.getItens();

			if(itens == null)
				itens = new ArrayList<>();
			
			itens.add(item);

			item = manager.merge(item);
			manager.merge(todo);
			
			URI uri = new URL(url + "todo/" + todo.getId() + "/item/" + item.getId()).toURI();

			return Response.ok(todo).status(201).location(uri).build();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			return Response.status(500).build();
		}
	}
	
	// Deleta
	@DELETE
	@Path("todo/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delTodo(@PathParam("id") long id) {
		try {
			Todo todo = manager.find(Todo.class, id);
			//manager.remove(todo);
			todo.setDel(true);
			manager.merge(todo);
			return Response.noContent().build();
		} catch(PersistenceException ex) {
			return Response.notModified().build();
		}
	}
	
	// Deleta
	@DELETE
	@Path("item/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delItem(@PathParam("id") long id) {
		try {
			Item item = manager.find(Item.class, id);
			manager.remove(item);
			return Response.noContent().build();
		} catch(PersistenceException ex) {
			return Response.notModified().build();
		}
	}

	// Altera todo
	@PUT
	@Path("todo/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTodo(Todo obj) {
		Todo todo = obj; //.getValue();
		try {
			todo = manager.merge(todo);
			
			URI uri = new URL(url + "todo/" + todo.getId()).toURI();

			return Response.ok().status(200).location(uri).build();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			return Response.status(500).build();
		}
	}

	// Altera item
	@PUT
	@Path("item/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateItem(Item obj) {
		Item item = obj; //.getValue();
		try {
			item = manager.merge(item);
			
			URI uri = new URL(url + "item/" + item.getId()).toURI();

			return Response.ok().status(200).location(uri).build();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			return Response.status(500).build();
		}
	}

}
