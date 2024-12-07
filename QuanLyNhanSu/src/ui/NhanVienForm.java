/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Các import cần thiết vẫn giữ nguyên
package ui;

import dao.ChucVuDAO;
import dao.ChucVuDAOImpl;
import dao.LuongDAO;
import dao.LuongDAOImpl;
import dao.PhongBanDAO;
import dao.PhongBanDAOImpl;
import dao.NhanVienDAO;
import dao.NhanVienDAOImpl;
import model.NhanVien;
import model.Luong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NhanVienForm extends JPanel {
    private JTextField txtMaNV, txtHoTen, txtGioiTinh, txtNgaySinh, txtDiaChi, txtSDT, txtEmail, txtChucVu, txtPhongBan, txtLuong;
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnThoat;
    private JTable table;
    private DefaultTableModel tableModel;

    private NhanVienDAO nhanVienDAO;
    private ChucVuDAO chucVuDAO;
    private PhongBanDAO phongBanDAO;
    private LuongDAO luongDAO;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Runnable exitAction;

    public NhanVienForm(Runnable exitAction) {
        this.exitAction = exitAction;
        initializeDAOs();
        setupUI();
        addEventListeners();
        hienThiDanhSachNhanVien();
    }

    private void initializeDAOs() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quan_ly_nhan_su", "root", "Quanvu@2003");
            nhanVienDAO = new NhanVienDAOImpl(conn);
            chucVuDAO = new ChucVuDAOImpl(conn);
            phongBanDAO = new PhongBanDAOImpl(conn);
            luongDAO = new LuongDAOImpl(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu.");
        }
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new GridLayout(5, 4, 10, 10));
        panelTop.setBorder(BorderFactory.createTitledBorder("QUẢN LÝ NHÂN VIÊN"));

        txtMaNV = new JTextField();
        txtHoTen = new JTextField();
        txtGioiTinh = new JTextField();
        txtNgaySinh = new JTextField();
        txtDiaChi = new JTextField();
        txtSDT = new JTextField();
        txtEmail = new JTextField();
        txtChucVu = new JTextField();
        txtPhongBan = new JTextField();
        txtLuong = new JTextField();

        panelTop.add(new JLabel("Mã NV:"));
        panelTop.add(txtMaNV);
        panelTop.add(new JLabel("Họ Tên:"));
        panelTop.add(txtHoTen);
        panelTop.add(new JLabel("Giới Tính:"));
        panelTop.add(txtGioiTinh);
        panelTop.add(new JLabel("Ngày Sinh:"));
        panelTop.add(txtNgaySinh);
        panelTop.add(new JLabel("Địa Chỉ:"));
        panelTop.add(txtDiaChi);
        panelTop.add(new JLabel("Số Điện Thoại:"));
        panelTop.add(txtSDT);
        panelTop.add(new JLabel("Email:"));
        panelTop.add(txtEmail);
        panelTop.add(new JLabel("Chức Vụ:"));
        panelTop.add(txtChucVu);
        panelTop.add(new JLabel("Phòng Ban:"));
        panelTop.add(txtPhongBan);
        panelTop.add(new JLabel("Lương:"));
        panelTop.add(txtLuong);

        add(panelTop, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã NV", "Họ Tên", "Giới Tính", "Ngày Sinh", "Địa Chỉ", "SĐT", "Email", "Chức Vụ", "Phòng Ban", "Lương"});
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

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

        add(panelBottom, BorderLayout.SOUTH);
    }

    private void addEventListeners() {
        btnThem.addActionListener(e -> themNhanVien());
        btnSua.addActionListener(e -> suaNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnTimKiem.addActionListener(e -> timKiemNhanVien());
        btnThoat.addActionListener(e -> {
            if (exitAction != null) {
                exitAction.run();
            }
        });
    }

    private void themNhanVien() {
        try {
            LocalDate ngaySinh = LocalDate.parse(txtNgaySinh.getText(), dateFormatter);
            NhanVien nhanVien = new NhanVien(
                    0, // Mã NV sẽ được tự động tạo khi thêm vào cơ sở dữ liệu
                    txtHoTen.getText(),
                    txtGioiTinh.getText(),
                    ngaySinh,
                    txtDiaChi.getText(),
                    txtSDT.getText(),
                    txtEmail.getText(),
                    Integer.parseInt(txtChucVu.getText()),
                    Integer.parseInt(txtPhongBan.getText()),
                    Integer.parseInt(txtLuong.getText())
            );
            nhanVienDAO.addNhanVien(nhanVien);
            hienThiDanhSachNhanVien();
            clearForm();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho Chức Vụ, Phòng Ban và Lương.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm nhân viên: " + ex.getMessage());
        }
    }

    private void suaNhanVien() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int maNV = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                LocalDate ngaySinh = LocalDate.parse(txtNgaySinh.getText(), dateFormatter);
                NhanVien nhanVien = new NhanVien(
                        maNV,
                        txtHoTen.getText(),
                        txtGioiTinh.getText(),
                        ngaySinh,
                        txtDiaChi.getText(),
                        txtSDT.getText(),
                        txtEmail.getText(),
                        Integer.parseInt(txtChucVu.getText()),
                        Integer.parseInt(txtPhongBan.getText()),
                        Integer.parseInt(txtLuong.getText())
                );
                nhanVienDAO.updateNhanVien(nhanVien);
                hienThiDanhSachNhanVien();
                clearForm();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho Chức Vụ, Phòng Ban và Lương.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi sửa nhân viên: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để sửa.");
        }
    }

    private void xoaNhanVien() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int maNV = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                nhanVienDAO.deleteNhanVien(maNV);
                hienThiDanhSachNhanVien();
                clearForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa nhân viên: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để xóa.");
        }
    }

    private void timKiemNhanVien() {
        String maNV = JOptionPane.showInputDialog(this, "Nhập mã nhân viên cần tìm:");
        if (maNV != null && !maNV.trim().isEmpty()) {
            try {
                NhanVien nhanVien = nhanVienDAO.getNhanVienById(Integer.parseInt(maNV));
                if (nhanVien != null) {
                    tableModel.setRowCount(0);
                    Luong luong = luongDAO.getLuongById(nhanVien.getLuongId());
                    tableModel.addRow(new Object[]{
                            nhanVien.getMaNhanVien(),
                            nhanVien.getHoTen(),
                            nhanVien.getGioiTinh(),
                            nhanVien.getNgaySinh(),
                            nhanVien.getDiaChi(),
                            nhanVien.getSdt(),
                            nhanVien.getEmail(),
                            nhanVien.getChucVuId(),
                            nhanVien.getPhongBanId(),
                            luong != null ? luong.getLuong() : "Không xác định"
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "Nhân viên không tồn tại.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên hợp lệ.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm nhân viên: " + ex.getMessage());
            }
        }
    }

    private void hienThiDanhSachNhanVien() {
        tableModel.setRowCount(0);
        try {
            List<NhanVien> nhanViens = nhanVienDAO.getAllNhanVien();
            for (NhanVien nv : nhanViens) {
                Luong luong = luongDAO.getLuongById(nv.getLuongId());
                tableModel.addRow(new Object[]{
                        nv.getMaNhanVien(),
                        nv.getHoTen(),
                        nv.getGioiTinh(),
                        nv.getNgaySinh(),
                        nv.getDiaChi(),
                        nv.getSdt(),
                        nv.getEmail(),
                        nv.getChucVuId(),
                        nv.getPhongBanId(),
                        luong != null ? luong.getLuong() : "Không xác định"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị danh sách nhân viên: " + ex.getMessage());
        }
    }

    private void clearForm() {
        txtMaNV.setText("");
        txtHoTen.setText("");
        txtGioiTinh.setText("");
        txtNgaySinh.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtChucVu.setText("");
        txtPhongBan.setText("");
        txtLuong.setText("");
    }
}
