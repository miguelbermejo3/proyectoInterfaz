package proyecto;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import proyecto.modelo.Usuario;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class AltaUserView extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldEmailNuevo;
	private JTextField textFieldNombreNuevo;
	private JTextField textFieldApellidoNuevo;
	private JPasswordField passwordFieldRepetir;
	private JPasswordField passwordFieldNuevo;

	public AltaUserView(App appController) {
		super(appController);
		setLayout(null);

		JLabel lblEmailNuevo = new JLabel("Email:");
		lblEmailNuevo.setBounds(236, 26, 46, 14);
		add(lblEmailNuevo);

		textFieldEmailNuevo = new JTextField();
		textFieldEmailNuevo.setBounds(236, 51, 116, 20);
		add(textFieldEmailNuevo);
		textFieldEmailNuevo.setColumns(10);

		JLabel lblContraseñaNueva = new JLabel("Contraseña:");
		lblContraseñaNueva.setBounds(236, 82, 86, 14);
		add(lblContraseñaNueva);

		JLabel lblRepetirContraseña = new JLabel("Repetir Contraseña");
		lblRepetirContraseña.setBounds(236, 137, 116, 14);
		add(lblRepetirContraseña);

		JLabel lblNombreNuevo = new JLabel("Nombre:");
		lblNombreNuevo.setBounds(236, 193, 46, 14);
		add(lblNombreNuevo);

		textFieldNombreNuevo = new JTextField();
		textFieldNombreNuevo.setBounds(236, 218, 116, 20);
		add(textFieldNombreNuevo);
		textFieldNombreNuevo.setColumns(10);

		JLabel lblApellidoNuevo = new JLabel("Apellidos");
		lblApellidoNuevo.setBounds(236, 249, 46, 14);
		add(lblApellidoNuevo);

		textFieldApellidoNuevo = new JTextField();
		textFieldApellidoNuevo.setBounds(236, 274, 116, 20);
		add(textFieldApellidoNuevo);
		textFieldApellidoNuevo.setColumns(10);

		JLabel lblCiclo = new JLabel("Ciclo Formativo");
		lblCiclo.setBounds(236, 305, 86, 14);
		add(lblCiclo);

		JComboBox<String> comboBoxCiclo = new JComboBox<String>();
		comboBoxCiclo.setBounds(236, 330, 116, 22);
		comboBoxCiclo.addItem("DAM");
		comboBoxCiclo.addItem("DAW");
		comboBoxCiclo.addItem("ASIR");
		comboBoxCiclo.setFocusable(false);

		add(comboBoxCiclo);

		JButton btnAceptar = new JButton("ACEPTAR");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Usuario u = new Usuario();
				char[] pass = passwordFieldNuevo.getPassword();
				String password = new String(pass);
				char[] pass2 = passwordFieldRepetir.getPassword();
				String password2 = new String(pass2);

				if (textFieldApellidoNuevo.getText().isEmpty() || textFieldEmailNuevo.getText().isEmpty()
						|| textFieldNombreNuevo.getText().isEmpty() || password.isEmpty() || password2.isEmpty()) {

					appController.altaUsuario(u);

				}

				if (!password.equals(password2)) {
					JOptionPane.showMessageDialog(null, "Contraseña mal introducida");
				} else {
					u = new Usuario();
					u.setActivo(true);
					u.setApellido(textFieldApellidoNuevo.getText());
					u.setCiclo((String) comboBoxCiclo.getSelectedItem());
					u.setCorreo(textFieldEmailNuevo.getText());
					u.setNombre(textFieldNombreNuevo.getText());

					u.setContraseña(password);

				}

			}
		});
		btnAceptar.setBounds(305, 426, 89, 23);
		add(btnAceptar);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldEmailNuevo.setText("");
				passwordFieldNuevo.setText("");
				passwordFieldRepetir.setText("");
				textFieldNombreNuevo.setText("");
				textFieldApellidoNuevo.setText("");

			}
		});
		btnCancelar.setBounds(425, 426, 89, 23);
		add(btnCancelar);

		passwordFieldRepetir = new JPasswordField();
		passwordFieldRepetir.setBounds(236, 162, 116, 20);
		add(passwordFieldRepetir);

		passwordFieldNuevo = new JPasswordField();
		passwordFieldNuevo.setBounds(236, 107, 116, 20);
		add(passwordFieldNuevo);

	}
}