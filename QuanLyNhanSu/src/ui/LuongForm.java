/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import dao.LuongDAO;
import dao.LuongDAOImpl;
import model.Luong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

// Lớp đại diện cho giao diện quản lý lương
public class LuongForm extends JPanel {
    // Khai báo các thành phần giao diện
    private JTextField txtMaLuong, txtHoTen, txtLuong, txtUngLuong;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnThoat;
    private JTable table;
    private DefaultTableModel tableModel;

    // DAO cho lương
    private LuongDAO luongDAO;
    private Runnable exitAction;

    // Constructor của lớp LuongForm
    public LuongForm(Runnable exitAction) {
        this.exitAction = exitAction;
        initializeDAOs(); // Khởi tạo các DAO
        setupUI(); // Thiết lập giao diện người dùng
        addEventListeners(); // Thêm các sự kiện cho các nút
        hienThiDanhSachLuong(); // Hiển thị danh sách lương khi khởi tạo
    }

    // Khởi tạo các DAO để kết nối với cơ sở dữ liệu
    private void initializeDAOs() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quan_ly_nhan_su", "root", "Quanvu@2003");
            luongDAO = new LuongDAOImpl(conn); // Tạo đối tượng LuongDAOImpl với kết nối cơ sở dữ liệu
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu."); // Hiển thị thông báo lỗi
        }
    }

    // Thiết lập giao diện người dùng
    private void setupUI() {
        setLayout(new BorderLayout()); // Thiết lập layout cho JPanel

        JPanel panelTop = new JPanel(new GridLayout(4, 2, 10, 10)); // Tạo panel cho các trường nhập liệu
        panelTop.setBorder(BorderFactory.createTitledBorder("QUẢN LÝ LƯƠNG")); // Đặt tiêu đề cho panel

        // Khởi tạo các trường nhập liệu
        txtMaLuong = new JTextField();
        txtHoTen = new JTextField();
        txtLuong = new JTextField();
        txtUngLuong = new JTextField();

        // Thêm các thành phần vào panelTop
        panelTop.add(new JLabel("Mã Nhân Viên:"));
        panelTop.add(txtMaLuong);
        panelTop.add(new JLabel("Họ Tên:"));
        panelTop.add(txtHoTen);
        panelTop.add(new JLabel("Lương:"));
        panelTop.add(txtLuong);
        panelTop.add(new JLabel("Ứng Lương:"));
        panelTop.add(txtUngLuong);

        // Thêm panelTop vào phần Bắc của JPanel chính
        add(panelTop, BorderLayout.NORTH);

        // Tạo DefaultTableModel và JTable để hiển thị danh sách lương
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã Nhân Viên", "Họ Tên", "Lương", "Ứng Lương"});
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Tạo panel cho các nút chức năng
        JPanel panelBottom = new JPanel();
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

        // Thêm panelBottom vào phần Nam của JPanel chính
        add(panelBottom, BorderLayout.SOUTH);
    }

    // Thêm các sự kiện cho các nút chức năng
    private void addEventListeners() {
        btnThem.addActionListener(e -> themLuong());
        btnSua.addActionListener(e -> suaLuong());
        btnXoa.addActionListener(e -> xoaLuong());
        btnTimKiem.addActionListener(e -> timKiemLuong());
        btnThoat.addActionListener(e -> {
            if (exitAction != null) {
                exitAction.run(); // Thực hiện hành động thoát nếu có
            }
        });
    }

    // Thêm một lương mới vào cơ sở dữ liệu
    private void themLuong() {
        try {
            Luong luong = new Luong(
                    Integer.parseInt(txtMaLuong.getText()),
                    txtHoTen.getText(),
                    Double.parseDouble(txtLuong.getText()),
                    Double.parseDouble(txtUngLuong.getText())
            );
            luongDAO.addLuong(luong); // Gọi phương thức thêm lương từ luongDAO
            hienThiDanhSachLuong(); // Cập nhật danh sách lương
            clearForm(); // Xóa các trường nhập liệu
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập các trường hợp lệ."); // Thông báo lỗi nếu định dạng số không hợp lệ
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm lương: " + ex.getMessage());
        }
    }

    // Sửa thông tin lương đã chọn
    private void suaLuong() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int maNhanVien = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                Luong luong = new Luong(
                        maNhanVien,
                        txtHoTen.getText(),
                        Double.parseDouble(txtLuong.getText()),
                        Double.parseDouble(txtUngLuong.getText())
                );
                luongDAO.updateLuong(luong); // Gọi phương thức sửa lương từ luongDAO
                hienThiDanhSachLuong(); // Cập nhật danh sách lương
                clearForm(); // Xóa các trường nhập liệu
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập các trường hợp lệ."); // Thông báo lỗi nếu định dạng số không hợp lệ
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi sửa lương: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lương để sửa."); // Thông báo nếu không có hàng nào được chọn
        }
    }

    // Xóa thông tin lương đã chọn
    private void xoaLuong() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int maNhanVien = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                luongDAO.deleteLuong(maNhanVien); // Gọi phương thức xóa lương từ luongDAO
                hienThiDanhSachLuong(); // Cập nhật danh sách lương
                clearForm(); // Xóa các trường nhập liệu
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa lương: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lương để xóa."); // Thông báo nếu không có hàng nào được chọn
        }
    }

    // Tìm kiếm lương theo mã lương
    private void timKiemLuong() {
        String maLuong = JOptionPane.showInputDialog(this, "Nhập mã lương cần tìm:");
        if (maLuong != null && !maLuong.trim().isEmpty()) {
            try {
                Luong luong = luongDAO.getLuongById(Integer.parseInt(maLuong)); // Tìm kiếm lương từ luongDAO
                if (luong != null) {
                    tableModel.setRowCount(0);
                    tableModel.addRow(new Object[]{
                            luong.getMaNhanVien(),
                            luong.getHoTen(),
                            luong.getLuong(),
                            luong.getUngLuong()
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy lương với mã: " + maLuong);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Mã lương không hợp lệ."); // Thông báo lỗi nếu mã lương không hợp lệ
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm lương: " + ex.getMessage());
            }
        }
    }

    // Hiển thị danh sách lương từ cơ sở dữ liệu
    private void hienThiDanhSachLuong() {
        try {
            tableModel.setRowCount(0); // Xóa các hàng hiện có trong bảng
            List<Luong> danhSachLuong = luongDAO.getAllLuongs(); // Lấy danh sách lương từ luongDAO
            for (Luong luong : danhSachLuong) {
                tableModel.addRow(new Object[]{
                        luong.getMaNhanVien(),
                        luong.getHoTen(),
                        luong.getLuong(),
                        luong.getUngLuong()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị danh sách lương: " + ex.getMessage());
        }
    }

    // Xóa các trường nhập liệu
    private void clearForm() {
        txtMaLuong.setText("");
        txtHoTen.setText("");
        txtLuong.setText("");
        txtUngLuong.setText("");
    }
}
