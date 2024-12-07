/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import dao.ChucVuDAO;
import dao.ChucVuDAOImpl;
import model.ChucVu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ChucVuForm extends JPanel {
    private JTextField txtMaChucVu, txtTenChucVu;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnThoat;
    private JTable table;
    private DefaultTableModel tableModel;

    private ChucVuDAO chucVuDAO; // DAO để tương tác với cơ sở dữ liệu cho chức vụ
    private Runnable exitAction; // Hành động để thoát giao diện

    public ChucVuForm(Runnable exitAction) {
        this.exitAction = exitAction; // Khởi tạo hành động thoát
        initializeDAOs(); // Khởi tạo DAO
        setupUI(); // Thiết lập giao diện người dùng
        addEventListeners(); // Thêm các sự kiện cho các nút
        hienThiDanhSachChucVu(); // Hiển thị danh sách chức vụ khi khởi tạo
    }

    private void initializeDAOs() {
        try {
            // Kết nối cơ sở dữ liệu
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quan_ly_nhan_su", "root", "Quanvu@2003");
            chucVuDAO = new ChucVuDAOImpl(conn); // Khởi tạo DAO
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi kết nối
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu."); // Hiển thị thông báo lỗi
        }
    }

    private void setupUI() {
        setLayout(new BorderLayout()); // Thiết lập layout chính

        JPanel panelTop = new JPanel(new GridLayout(2, 2, 10, 10)); // Panel trên với GridLayout
        panelTop.setBorder(BorderFactory.createTitledBorder("QUẢN LÝ CHỨC VỤ")); // Đặt tiêu đề cho panel

        // Tạo các trường nhập liệu cho mã chức vụ và tên chức vụ
        txtMaChucVu = new JTextField();
        txtTenChucVu = new JTextField();

        panelTop.add(new JLabel("Mã Chức Vụ:")); // Label cho mã chức vụ
        panelTop.add(txtMaChucVu); // Trường nhập liệu mã chức vụ
        panelTop.add(new JLabel("Tên Chức Vụ:")); // Label cho tên chức vụ
        panelTop.add(txtTenChucVu); // Trường nhập liệu tên chức vụ

        add(panelTop, BorderLayout.NORTH); // Thêm panel lên trên cùng

        // Tạo bảng và mô hình dữ liệu cho bảng
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã Chức Vụ", "Tên Chức Vụ"});
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER); // Thêm bảng vào phần giữa của giao diện

        JPanel panelBottom = new JPanel(); // Panel dưới với các nút chức năng
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTimKiem = new JButton("Tìm Kiếm");
        btnThoat = new JButton("Thoát");
        panelBottom.add(btnThem);
        panelBottom.add(btnSua);
        panelBottom.add(btnXoa);
        panelBottom.add(btnTimKiem);
        panelBottom.add(btnThoat);

        add(panelBottom, BorderLayout.SOUTH); // Thêm panel với các nút chức năng vào phần dưới cùng
    }

    private void addEventListeners() {
        // Thêm sự kiện cho các nút
        btnThem.addActionListener(e -> themChucVu());
        btnSua.addActionListener(e -> suaChucVu());
        btnXoa.addActionListener(e -> xoaChucVu());
        btnTimKiem.addActionListener(e -> timKiemChucVu());
        btnThoat.addActionListener(e -> {
            if (exitAction != null) {
                exitAction.run(); // Thực hiện hành động thoát khi nhấn nút Thoát
            }
        });
    }

    private void themChucVu() {
        try {
            int idChucVu = Integer.parseInt(txtMaChucVu.getText()); // Lấy mã chức vụ từ trường nhập liệu
            String tenChucVu = txtTenChucVu.getText(); // Lấy tên chức vụ từ trường nhập liệu
            if (tenChucVu.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên chức vụ không được để trống."); // Thông báo lỗi nếu tên chức vụ trống
                return;
            }
            // Tạo đối tượng ChucVu và thêm vào cơ sở dữ liệu
            ChucVu chucVu = new ChucVu(idChucVu, tenChucVu);
            chucVuDAO.addChucVu(chucVu);
            hienThiDanhSachChucVu(); // Cập nhật danh sách chức vụ
            clearForm(); // Xóa các trường nhập liệu
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã chức vụ hợp lệ."); // Thông báo lỗi khi mã chức vụ không hợp lệ
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm chức vụ: " + ex.getMessage()); // Hiển thị thông báo lỗi khác
        }
    }

    private void suaChucVu() {
        int selectedRow = table.getSelectedRow(); // Lấy hàng được chọn trong bảng
        if (selectedRow >= 0) { // Nếu có hàng được chọn
            try {
                int idChucVu = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()); // Lấy ID chức vụ từ bảng
                String tenChucVu = txtTenChucVu.getText(); // Lấy tên chức vụ từ trường nhập liệu
                if (tenChucVu.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Tên chức vụ không được để trống."); // Thông báo lỗi nếu tên chức vụ trống
                    return;
                }
                // Tạo đối tượng ChucVu và cập nhật thông tin trong cơ sở dữ liệu
                ChucVu chucVu = new ChucVu(idChucVu, tenChucVu);
                chucVuDAO.updateChucVu(chucVu);
                hienThiDanhSachChucVu(); // Cập nhật danh sách chức vụ
                clearForm(); // Xóa các trường nhập liệu
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ hợp lệ."); // Thông báo lỗi khi không có chức vụ được chọn
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi sửa chức vụ: " + ex.getMessage()); // Hiển thị thông báo lỗi khác
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ để sửa."); // Thông báo khi không có chức vụ được chọn
        }
    }

    private void xoaChucVu() {
        int selectedRow = table.getSelectedRow(); // Lấy hàng được chọn trong bảng
        if (selectedRow >= 0) { // Nếu có hàng được chọn
            try {
                int idChucVu = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()); // Lấy ID chức vụ từ bảng
                chucVuDAO.deleteChucVu(idChucVu); // Xóa chức vụ khỏi cơ sở dữ liệu
                hienThiDanhSachChucVu(); // Cập nhật danh sách chức vụ
                clearForm(); // Xóa các trường nhập liệu
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa chức vụ: " + ex.getMessage()); // Hiển thị thông báo lỗi khác
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ để xóa."); // Thông báo khi không có chức vụ được chọn
        }
    }

    private void timKiemChucVu() {
        String maChucVu = JOptionPane.showInputDialog(this, "Nhập mã chức vụ cần tìm:"); // Hiển thị hộp thoại nhập mã chức vụ
        if (maChucVu != null && !maChucVu.trim().isEmpty()) { // Nếu mã chức vụ không rỗng
            try {
                ChucVu chucVu = chucVuDAO.getChucVuById(Integer.parseInt(maChucVu)); // Tìm chức vụ theo ID
                if (chucVu != null) { // Nếu tìm thấy chức vụ
                    tableModel.setRowCount(0); // Xóa tất cả các hàng trong bảng
                    tableModel.addRow(new Object[]{
                            chucVu.getId(), // ID chức vụ
                            chucVu.getTenChucVu() // Tên chức vụ
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy chức vụ với mã: " + maChucVu); // Thông báo khi không tìm thấy chức vụ
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Mã chức vụ không hợp lệ."); // Thông báo lỗi khi mã chức vụ không hợp lệ
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm chức vụ: " + ex.getMessage()); // Hiển thị thông báo lỗi khác
            }
        }
    }

    private void hienThiDanhSachChucVu() {
        try {
            tableModel.setRowCount(0); // Xóa tất cả các hàng trong bảng
            List<ChucVu> danhSachChucVu = chucVuDAO.getAllChucVu(); // Lấy danh sách tất cả chức vụ
            for (ChucVu chucVu : danhSachChucVu) {
                tableModel.addRow(new Object[]{
                        chucVu.getId(), // ID chức vụ
                        chucVu.getTenChucVu() // Tên chức vụ
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị danh sách chức vụ: " + ex.getMessage()); // Hiển thị thông báo lỗi
        }
    }

    private void clearForm() {
        txtMaChucVu.setText(""); // Xóa trường mã chức vụ
        txtTenChucVu.setText(""); // Xóa trường tên chức vụ
    }
}
