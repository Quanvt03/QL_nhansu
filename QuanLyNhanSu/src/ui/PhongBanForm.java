/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import dao.PhongBanDAO;
import dao.PhongBanDAOImpl;
import model.PhongBan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

// Lớp đại diện cho giao diện quản lý phòng ban
public class PhongBanForm extends JPanel {
    // Khai báo các thành phần giao diện
    private JTextField txtMaPhongBan, txtTenPhongBan;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnThoat;
    private JTable table;
    private DefaultTableModel tableModel;

    private PhongBanDAO phongBanDAO; // Đối tượng DAO để tương tác với cơ sở dữ liệu
    private Runnable exitAction; // Hành động thoát

    // Constructor của lớp PhongBanForm
    public PhongBanForm(Runnable exitAction) {
        this.exitAction = exitAction; // Nhận hành động thoát từ bên ngoài
        initializeDAO(); // Khởi tạo DAO
        setupUI(); // Thiết lập giao diện
        addEventListeners(); // Thêm sự kiện cho các nút
        hienThiDanhSachPhongBan(); // Hiển thị danh sách phòng ban
    }

    // Phương thức để khởi tạo DAO và kết nối cơ sở dữ liệu
    private void initializeDAO() {
        try {
            // Kết nối đến cơ sở dữ liệu
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quan_ly_nhan_su", "root", "Quanvu@2003");
            phongBanDAO = new PhongBanDAOImpl(conn); // Khởi tạo DAO với kết nối cơ sở dữ liệu
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi kết nối ra console
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu."); // Hiển thị thông báo lỗi
        }
    }

    // Phương thức để thiết lập giao diện người dùng
    private void setupUI() {
        setLayout(new BorderLayout()); // Thiết lập layout cho JPanel

        // Khởi tạo panel trên cùng chứa các trường nhập liệu
        JPanel panelTop = new JPanel(new GridLayout(2, 2, 10, 10));
        panelTop.setBorder(BorderFactory.createTitledBorder("QUẢN LÝ PHÒNG BAN")); // Đặt tiêu đề cho panel

        txtMaPhongBan = new JTextField(); // Cho phép nhập mã phòng ban
        txtTenPhongBan = new JTextField();

        // Thêm các thành phần vào panelTop
        panelTop.add(new JLabel("Mã Phòng Ban:"));
        panelTop.add(txtMaPhongBan); // Thay đổi để cho phép nhập
        panelTop.add(new JLabel("Tên Phòng Ban:"));
        panelTop.add(txtTenPhongBan);

        add(panelTop, BorderLayout.NORTH); // Thêm panelTop vào phần Bắc của JPanel

        // Khởi tạo bảng và mô hình dữ liệu cho bảng
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã Phòng Ban", "Tên Phòng Ban"});
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER); // Thêm bảng vào phần Trung tâm của JPanel

        // Khởi tạo panel dưới cùng chứa các nút điều khiển
        JPanel panelBottom = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTimKiem = new JButton("Tìm Kiếm");
        btnThoat = new JButton("Thoát");

        // Thêm các nút vào panelBottom
        panelBottom.add(btnThem);
        panelBottom.add(btnSua);
        panelBottom.add(btnXoa);
        panelBottom.add(btnTimKiem);
        panelBottom.add(btnThoat);

        add(panelBottom, BorderLayout.SOUTH); // Thêm panelBottom vào phần Nam của JPanel
    }

    // Phương thức để thêm các sự kiện cho các nút
    private void addEventListeners() {
        btnThem.addActionListener(e -> themPhongBan()); // Thêm sự kiện cho nút Thêm
        btnSua.addActionListener(e -> suaPhongBan()); // Thêm sự kiện cho nút Sửa
        btnXoa.addActionListener(e -> xoaPhongBan()); // Thêm sự kiện cho nút Xóa
        btnTimKiem.addActionListener(e -> timKiemPhongBan()); // Thêm sự kiện cho nút Tìm Kiếm
        btnThoat.addActionListener(e -> {
            if (exitAction != null) {
                exitAction.run(); // Thực hiện hành động thoát nếu có
            }
        });

        // Thêm sự kiện khi người dùng chọn hàng trong bảng
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txtMaPhongBan.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtTenPhongBan.setText(tableModel.getValueAt(selectedRow, 1).toString());
                }
            }
        });
    }

    // Phương thức để thêm phòng ban mới
    private void themPhongBan() {
        try {
            PhongBan phongBan = new PhongBan(
                0, // Mã phòng ban sẽ được tự động tạo khi thêm vào cơ sở dữ liệu
                txtTenPhongBan.getText().trim() // Lấy tên phòng ban từ trường nhập
            );
            phongBanDAO.addPhongBan(phongBan); // Gọi DAO để thêm phòng ban
            hienThiDanhSachPhongBan(); // Cập nhật danh sách phòng ban
            clearForm(); // Xóa form
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm phòng ban: " + ex.getMessage()); // Hiển thị thông báo lỗi
        }
    }

    // Phương thức để sửa phòng ban đã chọn
    private void suaPhongBan() {
        int selectedRow = table.getSelectedRow(); // Lấy dòng được chọn trong bảng
        if (selectedRow >= 0) {
            try {
                int idPhongBan = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                PhongBan phongBan = new PhongBan(
                    idPhongBan,
                    txtTenPhongBan.getText().trim() // Lấy tên phòng ban từ trường nhập
                );
                phongBanDAO.updatePhongBan(phongBan); // Gọi DAO để sửa phòng ban
                hienThiDanhSachPhongBan(); // Cập nhật danh sách phòng ban
                clearForm(); // Xóa form
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng ban để sửa."); // Hiển thị thông báo lỗi
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi sửa phòng ban: " + ex.getMessage()); // Hiển thị thông báo lỗi
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng ban để sửa."); // Hiển thị thông báo lỗi
        }
    }

    // Phương thức để xóa phòng ban đã chọn
    private void xoaPhongBan() {
        int selectedRow = table.getSelectedRow(); // Lấy dòng được chọn trong bảng
        if (selectedRow >= 0) {
            try {
                int idPhongBan = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                phongBanDAO.deletePhongBan(idPhongBan); // Gọi DAO để xóa phòng ban
                hienThiDanhSachPhongBan(); // Cập nhật danh sách phòng ban
                clearForm(); // Xóa form
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa phòng ban: " + ex.getMessage()); // Hiển thị thông báo lỗi
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng ban để xóa."); // Hiển thị thông báo lỗi
        }
    }

    // Phương thức để tìm kiếm phòng ban theo tên
    private void timKiemPhongBan() {
        String tenPhongBan = JOptionPane.showInputDialog(this, "Nhập tên phòng ban cần tìm:"); // Nhập tên phòng ban từ người dùng
        if (tenPhongBan != null && !tenPhongBan.trim().isEmpty()) {
            try {
                List<PhongBan> danhSachPhongBan = phongBanDAO.searchPhongBanByName(tenPhongBan); // Gọi DAO để tìm kiếm phòng ban
                capNhatDanhSachPhongBan(danhSachPhongBan); // Cập nhật danh sách phòng ban
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm phòng ban: " + ex.getMessage()); // Hiển thị thông báo lỗi
            }
        }
    }

    // Phương thức để hiển thị danh sách phòng ban
    private void hienThiDanhSachPhongBan() {
        try {
            List<PhongBan> danhSachPhongBan = phongBanDAO.getAllPhongBans(); // Gọi DAO để lấy danh sách phòng ban
            capNhatDanhSachPhongBan(danhSachPhongBan); // Cập nhật danh sách phòng ban
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy danh sách phòng ban: " + ex.getMessage()); // Hiển thị thông báo lỗi
        }
    }

    // Phương thức để cập nhật danh sách phòng ban lên bảng
    private void capNhatDanhSachPhongBan(List<PhongBan> danhSachPhongBan) {
        tableModel.setRowCount(0); // Xóa tất cả các hàng trong bảng
        for (PhongBan pb : danhSachPhongBan) {
            tableModel.addRow(new Object[]{pb.getIdPhongBan(), pb.getTenPhongBan()}); // Thêm phòng ban vào bảng
        }
    }

    // Phương thức để xóa form nhập liệu
    private void clearForm() {
        txtMaPhongBan.setText(""); // Xóa trường mã phòng ban
        txtTenPhongBan.setText(""); // Xóa trường tên phòng ban
    }
}
