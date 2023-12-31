package proyecto;

import javax.swing.JLabel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import proyecto.modelo.Registro;
import proyecto.modelo.Usuario;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NuevoRegistroView extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblHorasSliders;
	private BigDecimal horas;

	public NuevoRegistroView(App appController, Usuario usuario) {

		super(appController);

		setLayout(null);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(133, 96, 46, 14);
		add(lblFecha);

		JComboBox<String> comboBoxFecha = new JComboBox<String>();
		comboBoxFecha.setBounds(133, 121, 185, 22);
		comboBoxFecha.setSelectedItem(null);
		List<String> fechas = null;
		fechas = appController.registroFechas();

		for (String fecha : fechas) {

			comboBoxFecha.addItem(fecha);
		}

		add(comboBoxFecha);

		JLabel lblHoras = new JLabel("Horas:");
		lblHoras.setBounds(133, 154, 46, 14);
		add(lblHoras);

		JSlider sliderHoras = new JSlider(0, 16, 0);
		sliderHoras.setMajorTickSpacing(1);
		sliderHoras.setBounds(133, 179, 245, 50);
		sliderHoras.setMinorTickSpacing(1);
		sliderHoras.setPaintTicks(true);
		sliderHoras.setPaintLabels(false);
		sliderHoras.setSnapToTicks(false);
		sliderHoras.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				horas = new BigDecimal(sliderHoras.getValue()).divide(new BigDecimal(2));
				lblHorasSliders.setText(String.valueOf(horas));

			}
		});
		add(sliderHoras);

		JLabel lblTareas = new JLabel("Tareas Realizadas:");
		lblTareas.setBounds(136, 240, 135, 22);
		add(lblTareas);

		JTextArea textAreaTareas = new JTextArea();

		JButton btnAceptar = new JButton("ACEPTAR");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy");
				LocalDate fechaActualizada = LocalDate.parse(comboBoxFecha.getSelectedItem().toString(), formato);
				Registro registro = new Registro();
				registro.setIdUsuario(appController.getUsuarioConectado().getIdUsuario());
				registro.setFecha(fechaActualizada);
				registro.setNumHoras(horas);
				registro.setDescripcion(textAreaTareas.getText());

				appController.crearRegistro(registro);

				comboBoxFecha.setSelectedItem(null);
				lblHoras.setText("");
				sliderHoras.setValue(0);
				textAreaTareas.setText("");

			}
		});
		btnAceptar.setBounds(432, 501, 89, 23);
		add(btnAceptar);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appController.irAPantallaBienvenida();
			}
		});
		btnCancelar.setBounds(541, 501, 109, 23);
		add(btnCancelar);

		lblHorasSliders = new JLabel("0");
		lblHorasSliders.setBounds(385, 194, 46, 14);

		add(lblHorasSliders);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(133, 273, 511, 211);
		add(scrollPane);

		scrollPane.setViewportView(textAreaTareas);

	}
}