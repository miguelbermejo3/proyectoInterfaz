package proyecto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import proyecto.modelo.Registro;

public class TableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private List<Registro> registros;
	private List<String> columnas;

	public TableModel() {
		registros = new ArrayList<>();
		columnas = new ArrayList<>();
		columnas.add("FECHA");
		columnas.add("HORAS");
		columnas.add("TAREAS");
	}

	@Override
	public int getRowCount() {
		return registros.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.size();
	}

	@Override
	public String getColumnName(int column) {
		return columnas.get(column);
	}

	@Override
	public Object getValueAt(int rowIndex, int column) {
		Registro registro = registros.get(rowIndex);
		String nombreColumn = columnas.get(column);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yy");

		if (nombreColumn.equals("FECHA")) {
			return formato.format(registro.getFecha());
		}
		if (nombreColumn.equals("HORAS")) {
			return registro.getNumHoras();
		}
		if (nombreColumn.equals("TAREAS")) {
			return registro.getDescripcion();
		}

		return "ERROR";
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}

	public List<String> getColumnas() {
		return columnas;
	}

	public void setColumnas(List<String> columnas) {
		this.columnas = columnas;
	}

}