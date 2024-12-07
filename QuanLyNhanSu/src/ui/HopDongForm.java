/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

// Import các lớp cần thiết cho chức năng của lớp HopDongForm
import dao.HopDongDAO;
import dao.HopDongDAOImpl;
import model.HopDong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

// Lớp đại diện cho giao diện quản lý hợp đồng
public class HopDongForm extends JPanel {
    // Khai báo các thành phần giao diện
    private JTextField txtSoHopDong, txtNgayBatDau, txtNgayKetThuc, txtNgayKy, txtMaNhanVien, txtThoiGianHopDong;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnThoat;
    private JTable table;
    private DefaultTableModel tableModel;

    // Khai báo đối tượng DAO để tương tác với cơ sở dữ liệu
    private HopDongDAO hopDongDAO;
    private Runnable exitAction;

    // Constructor của lớp HopDongForm
    public HopDongForm(Runnable exitAction) {
        this.exitAction = exitAction; // Lưu đối tượng Runnable để xử lý thoát
        initializeDAOs(); // Khởi tạo DAO
        setupUI(); // Thiết lập giao diện người dùng
        addEventListeners(); // Thêm các sự kiện cho các nút
        hienThiDanhSachHopDong(); // Hiển thị danh sách hợp đồng
    }

    // Khởi tạo DAO và kết nối đến cơ sở dữ liệu
    private void initializeDAOs() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quan_ly_nhan_su", "root", "Quanvu@2003");
            hopDongDAO = new HopDongDAOImpl(conn); // Khởi tạo DAO với kết nối cơ sở dữ liệu
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console nếu không kết nối được cơ sở dữ liệu
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu."); // Hiển thị thông báo lỗi
        }
    }

    // Thiết lập giao diện người dùng
    private void setupUI() {
        setLayout(new BorderLayout()); // Đặt layout của JPanel thành BorderLayout

        // Tạo JPanel để chứa các trường nhập liệu
        JPanel panelTop = new JPanel(new GridLayout(6, 2, 10, 10));
        panelTop.setBorder(BorderFactory.createTitledBorder("QUẢN LÝ HỢP ĐỒNG")); // Đặt tiêu đề cho panel

        // Khởi tạo các trường nhập liệu
        txtSoHopDong = new JTextField();
        txtNgayBatDau = new JTextField();
        txtNgayKetThuc = new JTextField();
        txtNgayKy = new JTextField();
        txtMaNhanVien = new JTextField(); // Sửa tên thành txtMaNhanVien
        txtThoiGianHopDong = new JTextField();

        // Thêm các trường nhập liệu vào panel
        panelTop.add(new JLabel("Số Hợp Đồng:"));
        panelTop.add(txtSoHopDong);
        panelTop.add(new JLabel("Ngày Bắt Đầu:"));
        panelTop.add(txtNgayBatDau);
        panelTop.add(new JLabel("Ngày Kết Thúc:"));
        panelTop.add(txtNgayKetThuc);
        panelTop.add(new JLabel("Ngày Ký:"));
        panelTop.add(txtNgayKy);
        panelTop.add(new JLabel("Mã Nhân Viên:")); // Sửa tên thành "Mã Nhân Viên"
        panelTop.add(txtMaNhanVien);
        panelTop.add(new JLabel("Thời Gian Hợp Đồng:"));
        panelTop.add(txtThoiGianHopDong);

        add(panelTop, BorderLayout.NORTH); // Thêm panelTop vào vị trí Bắc của BorderLayout

        // Thiết lập DefaultTableModel và JTable
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Số Hợp Đồng", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Ngày Ký", "Mã Nhân Viên", "Thời Gian Hợp Đồng"}); // Cập nhật tên cột
        table = new JTable(tableModel); // Khởi tạo JTable với tableModel
        add(new JScrollPane(table), BorderLayout.CENTER); // Thêm bảng vào vị trí Trung tâm của BorderLayout

        // Tạo JPanel để chứa các nút chức năng
        JPanel panelBottom = new JPanel();
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTimKiem = new JButton("Tìm Kiếm");
        btnThoat = new JButton("Thoát");
        panelBottom.add(btnThem); // Thêm nút Thêm vào panel
        panelBottom.add(btnSua); // Thêm nút Sửa vào panel
        panelBottom.add(btnXoa); // Thêm nút Xóa vào panel
        panelBottom.add(btnTimKiem); // Thêm nút Tìm Kiếm vào panel
        panelBottom.add(btnThoat); // Thêm nút Thoát vào panel

        add(panelBottom, BorderLayout.SOUTH); // Thêm panelBottom vào vị trí Nam của BorderLayout
    }

    // Thêm các sự kiện cho các nút
    private void addEventListeners() {
        btnThem.addActionListener(e -> themHopDong()); // Thêm sự kiện cho nút Thêm
        btnSua.addActionListener(e -> suaHopDong()); // Thêm sự kiện cho nút Sửa
        btnXoa.addActionListener(e -> xoaHopDong()); // Thêm sự kiện cho nút Xóa
        btnTimKiem.addActionListener(e -> timKiemHopDong()); // Thêm sự kiện cho nút Tìm Kiếm
        btnThoat.addActionListener(e -> { // Thêm sự kiện cho nút Thoát
            if (exitAction != null) {
                exitAction.run(); // Chạy hành động thoát nếu có
            }
        });
    }

    // Thêm hợp đồng mới vào cơ sở dữ liệu
    private void themHopDong() {
        try {
            int soHopDong = Integer.parseInt(txtSoHopDong.getText());
            int maNhanVien = Integer.parseInt(txtMaNhanVien.getText()); // Sửa tên thành maNhanVien
            HopDong hopDong = new HopDong(
                    soHopDong,
                    convertToDate(txtNgayBatDau.getText()),
                    convertToDate(txtNgayKetThuc.getText()),
                    convertToDate(txtNgayKy.getText()),
                    "", // Cập nhật giá trị này nếu có thuộc tính noiDung trong lớp HopDong
                    Integer.parseInt(txtThoiGianHopDong.getText()),
                    maNhanVien
            );
            hopDongDAO.themHopDong(hopDong); // Thêm hợp đồng vào cơ sở dữ liệu
            hienThiDanhSachHopDong(); // Cập nhật danh sách hợp đồng
            clearForm(); // Xóa các trường nhập liệu
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm hợp đồng: " + ex.getMessage()); // Hiển thị thông báo lỗi
        }
    }

    // Sửa thông tin hợp đồng đã chọn
    private void suaHopDong() {
        int selectedRow = table.getSelectedRow(); // Lấy hàng đã chọn
        if (selectedRow >= 0) {
            try {
                int soHopDong = (Integer) tableModel.getValueAt(selectedRow, 0); // Lấy số hợp đồng từ bảng
                int maNhanVien = Integer.parseInt(txtMaNhanVien.getText()); // Sửa tên thành maNhanVien
                HopDong hopDong = new HopDong(
                        soHopDong,
                        convertToDate(txtNgayBatDau.getText()),
                        convertToDate(txtNgayKetThuc.getText()),
                        convertToDate(txtNgayKy.getText()),
                        "", // Cập nhật giá trị này nếu có thuộc tính noiDung trong lớp HopDong
                        Integer.parseInt(txtThoiGianHopDong.getText()),
                        maNhanVien
                );
                hopDongDAO.suaHopDong(hopDong); // Sửa hợp đồng trong cơ sở dữ liệu
                hienThiDanhSachHopDong(); // Cập nhật danh sách hợp đồng
                clearForm(); // Xóa các trường nhập liệu
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi sửa hợp đồng: " + ex.getMessage()); // Hiển thị thông báo lỗi
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hợp đồng để sửa."); // Thông báo nếu không chọn hợp đồng
        }
    }

    // Xóa hợp đồng đã chọn
    private void xoaHopDong() {
        int selectedRow = table.getSelectedRow(); // Lấy hàng đã chọn
        if (selectedRow >= 0) {
            try {
                int soHopDong = (Integer) tableModel.getValueAt(selectedRow, 0); // Lấy số hợp đồng từ bảng
                hopDongDAO.xoaHopDong(soHopDong); // Xóa hợp đồng từ cơ sở dữ liệu
                hienThiDanhSachHopDong(); // Cập nhật danh sách hợp đồng
                clearForm(); // Xóa các trường nhập liệu
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa hợp đồng: " + ex.getMessage()); // Hiển thị thông báo lỗi
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hợp đồng để xóa."); // Thông báo nếu không chọn hợp đồng
        }
    }

    // Tìm kiếm hợp đồng theo số hợp đồng
    private void timKiemHopDong() {
        String soHopDongStr = JOptionPane.showInputDialog(this, "Nhập số hợp đồng cần tìm:"); // Hiển thị hộp thoại nhập số hợp đồng
        if (soHopDongStr != null && !soHopDongStr.trim().isEmpty()) {
            try {
                int soHopDong = Integer.parseInt(soHopDongStr); // Chuyển đổi số hợp đồng từ chuỗi
                HopDong hopDong = hopDongDAO.layHopDong(soHopDong); // Lấy hợp đồng từ cơ sở dữ liệu
                if (hopDong != null) {
                    tableModel.setRowCount(0); // Xóa các hàng hiện tại trong bảng
                    tableModel.addRow(new Object[]{
                            hopDong.getSoHopDong(),
                            hopDong.getNgayBatDau(),
                            hopDong.getNgayKetThuc(),
                            hopDong.getNgayKy(),
                            hopDong.getMaNhanVien(), // Cập nhật giá trị này nếu có thuộc tính maNhanVien trong lớp HopDong
                            hopDong.getThoiGian() // Sửa tên thành thoiGian
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy hợp đồng với số: " + soHopDong); // Thông báo nếu không tìm thấy hợp đồng
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm hợp đồng: " + ex.getMessage()); // Hiển thị thông báo lỗi
            }
        }
    }

    // Hiển thị danh sách hợp đồng từ cơ sở dữ liệu
    private void hienThiDanhSachHopDong() {
        try {
            tableModel.setRowCount(0); // Xóa các hàng hiện tại trong bảng
            List<HopDong> danhSachHopDong = hopDongDAO.layDanhSachHopDong(); // Lấy danh sách hợp đồng từ cơ sở dữ liệu
            for (HopDong hopDong : danhSachHopDong) {
                tableModel.addRow(new Object[]{
                        hopDong.getSoHopDong(),
                        hopDong.getNgayBatDau(),
                        hopDong.getNgayKetThuc(),
                        hopDong.getNgayKy(),
                        hopDong.getMaNhanVien(), // Cập nhật giá trị này nếu có thuộc tính maNhanVien trong lớp HopDong
                        hopDong.getThoiGian() // Sửa tên thành thoiGian
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị danh sách hợp đồng: " + ex.getMessage()); // Hiển thị thông báo lỗi
        }
    }

    // Xóa các trường nhập liệu
    private void clearForm() {
        txtSoHopDong.setText("");
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");
        txtNgayKy.setText("");
        txtMaNhanVien.setText(""); // Sửa tên thành txtMaNhanVien
        txtThoiGianHopDong.setText("");
    }

    // Chuyển đổi chuỗi ngày thành đối tượng Date
    private Date convertToDate(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày
            java.util.Date date = sdf.parse(dateString); // Phân tích chuỗi ngày
            return new Date(date.getTime()); // Chuyển đổi thành java.sql.Date
        } catch (ParseException e) {
            e.printStackTrace(); // In lỗi ra console nếu phân tích ngày không thành công
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ."); // Hiển thị thông báo lỗi
            return null;
        }
    }
}
