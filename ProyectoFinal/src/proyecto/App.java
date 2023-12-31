package proyecto;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import proyecto.modelo.Fecha;
import proyecto.modelo.Registro;
import proyecto.modelo.Usuario;
import proyecto.service.FechaService;
import proyecto.service.RegistroService;
import proyecto.service.UsuarioService;
import proyecto.service.autenticarUsuarioException;
import proyecto.service.fctException;

public class App {

	private JFrame frame;
	private LoginView pantallaLogin;
	private AltaUserView pantallaAltaUser;
	private BienvenidaView pantallaBienvenida;
	private ConsultarRegistroView pantallaConsulta;
	private NuevoRegistroView pantallaNuevoRegistro;
	private JMenuBar menuBar;
	private Usuario usuarioConectado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws fctException
	 */
	public App() {
		initialize();
		usuarioConectado = new Usuario();
		pantallaLogin = new LoginView(this);
		pantallaAltaUser = new AltaUserView(this);

		irAPantallaLogin();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setBounds(700, 700, 750, 600);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnApp = new JMenu("App");
		menuBar.add(mnApp);

		JMenuItem mntmCerrarSesion = new JMenuItem("Cerrar sesión");
		ActionListener listenerCerrar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				irAPantallaLogin();

			}
		};
		mntmCerrarSesion.addActionListener(listenerCerrar);
		mnApp.add(mntmCerrarSesion);

		JMenuItem mntmSalir = new JMenuItem("Salir");
		ActionListener listenerSalir = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		};
		mntmSalir.addActionListener(listenerSalir);
		mnApp.add(mntmSalir);

		JMenu mnRegistro = new JMenu("Registros");
		menuBar.add(mnRegistro);

		JMenuItem mntmCrearRegistro = new JMenuItem("Crear nuevo registro");
		ActionListener listenerRegistro = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				irAPantallaNuevoRegistro();

			}
		};
		mntmCrearRegistro.addActionListener(listenerRegistro);
		mnRegistro.add(mntmCrearRegistro);

		JMenuItem mntmConsultarRegistro = new JMenuItem("Consultar registros");

		ActionListener listenerConsultaRegistro = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				irAPantallaConsultaRegistro();

			}
		};
		mntmConsultarRegistro.addActionListener(listenerConsultaRegistro);
		mnRegistro.add(mntmConsultarRegistro);

	}

	public void irAPantallaLogin() {

		frame.setContentPane(pantallaLogin);
		menuBar.setVisible(false);
		frame.revalidate();

	}

	public void irAPantallaAltaUser() {

		frame.setContentPane(pantallaAltaUser);
		menuBar.setVisible(false);
		frame.revalidate();

	}

	public void irAPantallaBienvenida() {

		menuBar.setVisible(true);
		pantallaBienvenida = new BienvenidaView(this, usuarioConectado);
		frame.setContentPane(pantallaBienvenida);
		frame.revalidate();
	}

	public void irAPantallaConsultaRegistro() {
		pantallaConsulta = new ConsultarRegistroView(this);
		frame.setContentPane(pantallaConsulta);
		menuBar.setVisible(true);
		frame.revalidate();
	}

	public void irAPantallaNuevoRegistro() {
		pantallaNuevoRegistro = new NuevoRegistroView(this, usuarioConectado);
		frame.setContentPane(pantallaNuevoRegistro);
		menuBar.setVisible(true);
		frame.revalidate();
	}

	public void loginUsuario(Usuario u) {

		UsuarioService us = new UsuarioService();
		Usuario usuario = new Usuario();
		try {

			usuario = us.login(u.getCorreo(), u.getContraseña());
			System.out.println(usuario.getIdUsuario());
			this.usuarioConectado = usuario;

			irAPantallaBienvenida();
		} catch (fctException | autenticarUsuarioException e1) {
			JOptionPane.showMessageDialog(null, "No existe el Usuario introducido o contraseña inválida",
					e1.getMessage(), JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();

		}

	}

	public void altaUsuario(Usuario u) {

		UsuarioService us = new UsuarioService();

		try {

			try {
				us.altaUsuario(u);
			} catch (autenticarUsuarioException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.usuarioConectado = u;
			irAPantallaBienvenida();

			this.usuarioConectado = u;
		} catch (fctException e1) {

			JOptionPane.showMessageDialog(null, "Error al crear el usuario", e1.getMessage(),
					JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}

	}

	public List<String> registroFechas() {

		FechaService fs = new FechaService();

		List<String> fechaString = new ArrayList<>();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy");

		List<Fecha> fechas;
		try {
			fechas = fs.consultarFechasActuales();
			for (Fecha fecha : fechas) {
				fechaString.add(formato.format(fecha.getFecha()));
			}
			return fechaString;
		} catch (fctException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public void crearRegistro(Registro registro) {

		RegistroService rs = new RegistroService();

		try {
			registro.setIdUsuario(usuarioConectado.getIdUsuario());
			rs.crearRegistroUsuario(registro);
		} catch (fctException e) {
			JOptionPane.showMessageDialog(null, "El registro ya existe", e.getMessage(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	public List<Registro> consultarRegistro() {
		RegistroService rs = new RegistroService();
		List<Registro> registros = new ArrayList<Registro>();
		try {
			registros = rs.consultarRegistroUsuario(usuarioConectado.getIdUsuario());

		} catch (fctException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (autenticarUsuarioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return registros;

	}

	public Usuario getUsuarioConectado() {
		return usuarioConectado;
	}

	public void setUsuarioConectado(Usuario usuarioConectado) {
		this.usuarioConectado = usuarioConectado;
	}

}