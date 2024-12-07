/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import dao.BaoHiemDAO; // Import interface DAO cho bảo hiểm
import dao.BaoHiemDAOImpl; // Import implementation của DAO cho bảo hiểm
import dao.NhanVienDAO; // Import interface DAO cho nhân viên
import dao.NhanVienDAOImpl; // Import implementation của DAO cho nhân viên
import model.BaoHiem; // Import lớp mô hình cho bảo hiểm
import model.NhanVien; // Import lớp mô hình cho nhân viên

import javax.swing.*; // Import các thành phần GUI cơ bản
import javax.swing.table.DefaultTableModel; // Import lớp DefaultTableModel để làm việc với bảng
import java.awt.*; // Import các lớp cho bố cục và thành phần giao diện
import java.awt.event.ActionEvent; // Import lớp ActionEvent cho sự kiện nút bấm
import java.awt.event.ActionListener; // Import interface ActionListener cho xử lý sự kiện
import java.sql.Connection; // Import lớp Connection cho kết nối cơ sở dữ liệu
import java.sql.DriverManager; // Import lớp DriverManager để tạo kết nối cơ sở dữ liệu
import java.sql.SQLException; // Import lớp SQLException để xử lý lỗi cơ sở dữ liệu
import java.util.List; // Import lớp List để làm việc với danh sách

public class BaoHiemForm extends JPanel { // Khai báo lớp BaoHiemForm mở rộng JPanel
    private JTextField txtSoBaoHiem, txtNgayCap, txtNoiCap, txtMaNhanVien; // Các trường nhập liệu
    private JButton btnThem, btnSua, btnXoa, btnTimKiem, btnThoat; // Các nút chức năng
    private JTable table; // Bảng hiển thị danh sách bảo hiểm
    private DefaultTableModel tableModel; // Model của bảng để quản lý dữ liệu bảng

    private BaoHiemDAO baoHiemDAO; // DAO để làm việc với bảng bảo hiểm
    private NhanVienDAO nhanVienDAO; // DAO để làm việc với bảng nhân viên

    private Runnable exitAction; // Action để thực hiện khi thoát

    public BaoHiemForm(Runnable exitAction) { // Constructor nhận một Runnable để xử lý thoát
        this.exitAction = exitAction; // Lưu action thoát
        initializeDAOs(); // Khởi tạo các DAO
        setupUI(); // Thiết lập giao diện người dùng
        addEventListeners(); // Thêm các trình xử lý sự kiện cho các nút
        hienThiDanhSachBaoHiem(); // Hiển thị danh sách bảo hiểm
    }

    private void initializeDAOs() { // Khởi tạo các DAO
        try {
            // Tạo kết nối tới cơ sở dữ liệu MySQL
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quan_ly_nhan_su", "root", "Quanvu@2003");
            baoHiemDAO = new BaoHiemDAOImpl(conn); // Khởi tạo DAO cho bảo hiểm
            nhanVienDAO = new NhanVienDAOImpl(conn); // Khởi tạo DAO cho nhân viên
        } catch (SQLException e) { // Xử lý lỗi kết nối cơ sở dữ liệu
            e.printStackTrace(); // In lỗi ra console
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến cơ sở dữ liệu."); // Hiển thị thông báo lỗi
        }
    }

    private void setupUI() { // Thiết lập giao diện người dùng
        setLayout(new BorderLayout()); // Sử dụng BorderLayout cho JPanel

        // Panel chứa các trường nhập liệu
        JPanel panelTop = new JPanel(new GridLayout(4, 2, 10, 10)); // GridLayout với 4 hàng, 2 cột
        panelTop.setBorder(BorderFactory.createTitledBorder("QUẢN LÝ BẢO HIỂM")); // Thiết lập tiêu đề cho panel

        // Khởi tạo các trường nhập liệu
        txtSoBaoHiem = new JTextField();
        txtNgayCap = new JTextField();
        txtNoiCap = new JTextField();
        txtMaNhanVien = new JTextField();

        // Thêm các trường nhập liệu vào panel
        panelTop.add(new JLabel("Số BH:")); // Label cho Số BH
        panelTop.add(txtSoBaoHiem); // Trường nhập liệu cho Số BH
        panelTop.add(new JLabel("Ngày Cấp:")); // Label cho Ngày Cấp
        panelTop.add(txtNgayCap); // Trường nhập liệu cho Ngày Cấp
        panelTop.add(new JLabel("Nơi Cấp:")); // Label cho Nơi Cấp
        panelTop.add(txtNoiCap); // Trường nhập liệu cho Nơi Cấp
        panelTop.add(new JLabel("Mã NV:")); // Label cho Mã NV
        panelTop.add(txtMaNhanVien); // Trường nhập liệu cho Mã NV

        add(panelTop, BorderLayout.NORTH); // Thêm panelTop vào phía Bắc của BorderLayout

        // Bảng hiển thị danh sách bảo hiểm
        tableModel = new DefaultTableModel(); // Khởi tạo DefaultTableModel
        tableModel.setColumnIdentifiers(new String[]{"ID BH", "Số BH", "Ngày Cấp", "Nơi Cấp", "Mã NV"}); // Thiết lập tên cột cho bảng
        table = new JTable(tableModel); // Khởi tạo bảng với tableModel
        add(new JScrollPane(table), BorderLayout.CENTER); // Thêm bảng vào giữa của BorderLayout với cuộn

        // Panel chứa các nút chức năng
        JPanel panelBottom = new JPanel(); // Panel cho các nút
        btnThem = new JButton("Thêm"); // Nút thêm
        btnSua = new JButton("Sửa"); // Nút sửa
        btnXoa = new JButton("Xóa"); // Nút xóa
        btnTimKiem = new JButton("Tìm Kiếm"); // Nút tìm kiếm
        btnThoat = new JButton("Thoát"); // Nút thoát
        panelBottom.add(btnThem); // Thêm nút thêm vào panel
        panelBottom.add(btnSua); // Thêm nút sửa vào panel
        panelBottom.add(btnXoa); // Thêm nút xóa vào panel
        panelBottom.add(btnTimKiem); // Thêm nút tìm kiếm vào panel
        panelBottom.add(btnThoat); // Thêm nút thoát vào panel

        add(panelBottom, BorderLayout.SOUTH); // Thêm panelBottom vào phía Nam của BorderLayout
    }

    private void addEventListeners() { // Thêm các sự kiện cho các nút
        btnThem.addActionListener(e -> themBaoHiem()); // Thêm sự kiện cho nút thêm
        btnSua.addActionListener(e -> suaBaoHiem()); // Thêm sự kiện cho nút sửa
        btnXoa.addActionListener(e -> xoaBaoHiem()); // Thêm sự kiện cho nút xóa
        btnTimKiem.addActionListener(e -> timKiemBaoHiem()); // Thêm sự kiện cho nút tìm kiếm
        btnThoat.addActionListener(e -> {
            if (exitAction != null) { // Nếu có action thoát
                exitAction.run(); // Thực thi action thoát
            }
        });
    }

    private void themBaoHiem() { // Thêm bảo hiểm mới
        try {
            BaoHiem baoHiem = new BaoHiem(
                0, // ID BH sẽ được tự động tạo khi thêm vào cơ sở dữ liệu
                txtSoBaoHiem.getText(), // Lấy số bảo hiểm từ trường nhập liệu
                java.time.LocalDate.parse(txtNgayCap.getText()), // Chuyển đổi ngày cấp từ chuỗi
                txtNoiCap.getText(), // Lấy nơi cấp từ trường nhập liệu
                Integer.parseInt(txtMaNhanVien.getText()) // Chuyển đổi mã nhân viên từ chuỗi
            );
            baoHiemDAO.addBaoHiem(baoHiem); // Thêm bảo hiểm vào cơ sở dữ liệu
            hienThiDanhSachBaoHiem(); // Cập nhật danh sách bảo hiểm
            clearForm(); // Xóa các trường nhập liệu
        } catch (NumberFormatException ex) { // Xử lý lỗi khi nhập mã nhân viên không hợp lệ
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho Mã NV."); // Hiển thị thông báo lỗi
        } catch (Exception ex) { // Xử lý lỗi khác
            JOptionPane.showMessageDialog(this, "Lỗi khi thêm bảo hiểm: " + ex.getMessage()); // Hiển thị thông báo lỗi
        }
    }

    private void suaBaoHiem() { // Sửa bảo hiểm đã chọn
        int selectedRow = table.getSelectedRow(); // Lấy hàng được chọn trong bảng
        if (selectedRow >= 0) { // Nếu có hàng được chọn
            try {
                int idBaoHiem = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()); // Lấy ID bảo hiểm từ bảng
                BaoHiem baoHiem = new BaoHiem(
                    idBaoHiem, // ID bảo hiểm
                    txtSoBaoHiem.getText(), // Lấy số bảo hiểm từ trường nhập liệu
                    java.time.LocalDate.parse(txtNgayCap.getText()), // Chuyển đổi ngày cấp từ chuỗi
                    txtNoiCap.getText(), // Lấy nơi cấp từ trường nhập liệu
                    Integer.parseInt(txtMaNhanVien.getText()) // Chuyển đổi mã nhân viên từ chuỗi
                );
                baoHiemDAO.updateBaoHiem(baoHiem); // Cập nhật thông tin bảo hiểm trong cơ sở dữ liệu
                hienThiDanhSachBaoHiem(); // Cập nhật danh sách bảo hiểm
                clearForm(); // Xóa các trường nhập liệu
            } catch (NumberFormatException ex) { // Xử lý lỗi khi nhập mã nhân viên không hợp lệ
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho Mã NV."); // Hiển thị thông báo lỗi
            } catch (Exception ex) { // Xử lý lỗi khác
                JOptionPane.showMessageDialog(this, "Lỗi khi sửa bảo hiểm: " + ex.getMessage()); // Hiển thị thông báo lỗi
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bảo hiểm để sửa."); // Thông báo khi không có bảo hiểm được chọn
        }
    }

    private void xoaBaoHiem() { // Xóa bảo hiểm đã chọn
        int selectedRow = table.getSelectedRow(); // Lấy hàng được chọn trong bảng
        if (selectedRow >= 0) { // Nếu có hàng được chọn
            try {
                int idBaoHiem = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()); // Lấy ID bảo hiểm từ bảng
                baoHiemDAO.deleteBaoHiem(idBaoHiem); // Xóa bảo hiểm khỏi cơ sở dữ liệu
                hienThiDanhSachBaoHiem(); // Cập nhật danh sách bảo hiểm
                clearForm(); // Xóa các trường nhập liệu
            } catch (Exception ex) { // Xử lý lỗi
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa bảo hiểm: " + ex.getMessage()); // Hiển thị thông báo lỗi
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bảo hiểm để xóa."); // Thông báo khi không có bảo hiểm được chọn
        }
    }

    private void timKiemBaoHiem() { // Tìm kiếm bảo hiểm theo ID
        String idBaoHiem = JOptionPane.showInputDialog(this, "Nhập ID bảo hiểm cần tìm:"); // Hiển thị hộp thoại để nhập ID bảo hiểm
        if (idBaoHiem != null && !idBaoHiem.trim().isEmpty()) { // Nếu ID không rỗng
            try {
                BaoHiem baoHiem = baoHiemDAO.getBaoHiemById(Integer.parseInt(idBaoHiem)); // Tìm bảo hiểm theo ID
                if (baoHiem != null) { // Nếu tìm thấy bảo hiểm
                    tableModel.setRowCount(0); // Xóa tất cả các hàng trong bảng
                    tableModel.addRow(new Object[]{
                        baoHiem.getIdBaoHiem(), // ID bảo hiểm
                        baoHiem.getSoBaoHiem(), // Số bảo hiểm
                        baoHiem.getNgayCap(), // Ngày cấp
                        baoHiem.getNoiCap(), // Nơi cấp
                        baoHiem.getMaNhanVien() // Mã nhân viên
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy bảo hiểm với ID: " + idBaoHiem); // Thông báo khi không tìm thấy bảo hiểm
                }
            } catch (NumberFormatException ex) { // Xử lý lỗi khi ID không hợp lệ
                JOptionPane.showMessageDialog(this, "ID bảo hiểm không hợp lệ."); // Hiển thị thông báo lỗi
            } catch (Exception ex) { // Xử lý lỗi khác
                JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm bảo hiểm: " + ex.getMessage()); // Hiển thị thông báo lỗi
            }
        }
    }

    private void hienThiDanhSachBaoHiem() { // Hiển thị danh sách tất cả bảo hiểm
        try {
            tableModel.setRowCount(0); // Xóa tất cả các hàng trong bảng
            List<BaoHiem> danhSachBaoHiem = baoHiemDAO.getAllBaoHiem(); // Lấy tất cả bảo hiểm từ cơ sở dữ liệu
            for (BaoHiem baoHiem : danhSachBaoHiem) { // Duyệt qua danh sách bảo hiểm
                tableModel.addRow(new Object[]{
                    baoHiem.getIdBaoHiem(), // ID bảo hiểm
                    baoHiem.getSoBaoHiem(), // Số bảo hiểm
                    baoHiem.getNgayCap(), // Ngày cấp
                    baoHiem.getNoiCap(), // Nơi cấp
                    baoHiem.getMaNhanVien() // Mã nhân viên
                });
            }
        } catch (Exception ex) { // Xử lý lỗi
            JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị danh sách bảo hiểm: " + ex.getMessage()); // Hiển thị thông báo lỗi
        }
    }

    private void clearForm() { // Xóa tất cả các trường nhập liệu
        txtSoBaoHiem.setText(""); // Xóa trường Số BH
        txtNgayCap.setText(""); // Xóa trường Ngày Cấp
        txtNoiCap.setText(""); // Xóa trường Nơi Cấp
        txtMaNhanVien.setText(""); // Xóa trường Mã NV
    }
}
