/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao; // Khai báo package dao, nơi lưu trữ các lớp liên quan đến thao tác với cơ sở dữ liệu

import model.HopDong; // Import lớp HopDong từ package model để sử dụng trong các phương thức của HopDongDAOImpl
import java.sql.*; // Import các lớp liên quan đến SQL để thao tác với cơ sở dữ liệu
import java.util.ArrayList; // Import lớp ArrayList để lưu trữ danh sách hợp đồng
import java.util.List; // Import giao diện List để sử dụng làm kiểu trả về cho danh sách hợp đồng
import java.util.logging.Level; // Import lớp Level để định nghĩa mức độ ghi log
import java.util.logging.Logger; // Import lớp Logger để ghi log các thông tin và lỗi

public class HopDongDAOImpl implements HopDongDAO { // Định nghĩa lớp HopDongDAOImpl thực hiện các phương thức từ giao diện HopDongDAO
    private static final Logger LOGGER = Logger.getLogger(HopDongDAOImpl.class.getName()); // Khai báo Logger để ghi log lỗi và thông tin
    private Connection connection; // Khai báo đối tượng Connection để kết nối đến cơ sở dữ liệu

    public HopDongDAOImpl(Connection connection) { // Constructor để khởi tạo đối tượng HopDongDAOImpl với kết nối cơ sở dữ liệu
        this.connection = connection;
    }

    @Override
    public void themHopDong(HopDong hopDong) { // Phương thức thêm hợp đồng vào cơ sở dữ liệu
        String sql = "INSERT INTO hop_dong (ngay_bat_dau, ngay_ket_thuc, ngay_ky, noi_dung, thoi_gian, ma_nhan_vien) VALUES (?, ?, ?, ?, ?, ?)"; // Câu lệnh SQL để thêm hợp đồng
        try (PreparedStatement stmt = connection.prepareStatement(sql)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setDate(1, hopDong.getNgayBatDau()); // Đặt giá trị cho tham số ngày bắt đầu
            stmt.setDate(2, hopDong.getNgayKetThuc()); // Đặt giá trị cho tham số ngày kết thúc
            stmt.setDate(3, hopDong.getNgayKy()); // Đặt giá trị cho tham số ngày ký
            stmt.setString(4, hopDong.getNoiDung()); // Đặt giá trị cho tham số nội dung hợp đồng
            stmt.setInt(5, hopDong.getThoiGian()); // Đặt giá trị cho tham số thời gian
            stmt.setInt(6, hopDong.getMaNhanVien()); // Đặt giá trị cho tham số mã nhân viên
            stmt.executeUpdate(); // Thực thi câu lệnh SQL để thêm hợp đồng
        } catch (SQLException e) { // Bắt lỗi SQLException
            LOGGER.log(Level.SEVERE, "Error inserting contract", e); // Ghi log lỗi với mức độ SEVERE
            throw new RuntimeException("Error inserting contract", e); // Ném lỗi RuntimeException nếu có lỗi
        }
    }

    @Override
    public void suaHopDong(HopDong hopDong) { // Phương thức cập nhật thông tin hợp đồng trong cơ sở dữ liệu
        String sql = "UPDATE hop_dong SET ngay_bat_dau = ?, ngay_ket_thuc = ?, ngay_ky = ?, noi_dung = ?, thoi_gian = ?, ma_nhan_vien = ? WHERE so_hop_dong = ?"; // Câu lệnh SQL để cập nhật hợp đồng
        try (PreparedStatement stmt = connection.prepareStatement(sql)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setDate(1, hopDong.getNgayBatDau()); // Đặt giá trị cho tham số ngày bắt đầu
            stmt.setDate(2, hopDong.getNgayKetThuc()); // Đặt giá trị cho tham số ngày kết thúc
            stmt.setDate(3, hopDong.getNgayKy()); // Đặt giá trị cho tham số ngày ký
            stmt.setString(4, hopDong.getNoiDung()); // Đặt giá trị cho tham số nội dung hợp đồng
            stmt.setInt(5, hopDong.getThoiGian()); // Đặt giá trị cho tham số thời gian
            stmt.setInt(6, hopDong.getMaNhanVien()); // Đặt giá trị cho tham số mã nhân viên
            stmt.setInt(7, hopDong.getSoHopDong()); // Đặt giá trị cho tham số số hợp đồng (ID)
            stmt.executeUpdate(); // Thực thi câu lệnh SQL để cập nhật hợp đồng
        } catch (SQLException e) { // Bắt lỗi SQLException
            LOGGER.log(Level.SEVERE, "Error updating contract", e); // Ghi log lỗi với mức độ SEVERE
            throw new RuntimeException("Error updating contract", e); // Ném lỗi RuntimeException nếu có lỗi
        }
    }

    @Override
    public void xoaHopDong(int soHopDong) { // Phương thức xóa hợp đồng dựa trên số hợp đồng (ID)
        String sql = "DELETE FROM hop_dong WHERE so_hop_dong = ?"; // Câu lệnh SQL để xóa hợp đồng
        try (PreparedStatement stmt = connection.prepareStatement(sql)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setInt(1, soHopDong); // Đặt giá trị cho tham số số hợp đồng (ID)
            stmt.executeUpdate(); // Thực thi câu lệnh SQL để xóa hợp đồng
        } catch (SQLException e) { // Bắt lỗi SQLException
            LOGGER.log(Level.SEVERE, "Error deleting contract", e); // Ghi log lỗi với mức độ SEVERE
            throw new RuntimeException("Error deleting contract", e); // Ném lỗi RuntimeException nếu có lỗi
        }
    }

    @Override
    public List<HopDong> layDanhSachHopDong() { // Phương thức lấy danh sách tất cả các hợp đồng từ cơ sở dữ liệu
        List<HopDong> hopDongs = new ArrayList<>(); // Khởi tạo danh sách hợp đồng
        String sql = "SELECT * FROM hop_dong"; // Câu lệnh SQL để lấy toàn bộ hợp đồng
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) { // Tạo đối tượng Statement và thực thi câu lệnh SQL
            while (rs.next()) { // Duyệt qua từng dòng kết quả
                HopDong hopDong = new HopDong(
                    rs.getInt("so_hop_dong"), // Lấy giá trị số hợp đồng (ID)
                    rs.getDate("ngay_bat_dau"), // Lấy giá trị ngày bắt đầu
                    rs.getDate("ngay_ket_thuc"), // Lấy giá trị ngày kết thúc
                    rs.getDate("ngay_ky"), // Lấy giá trị ngày ký
                    rs.getString("noi_dung"), // Lấy giá trị nội dung hợp đồng
                    rs.getInt("thoi_gian"), // Lấy giá trị thời gian
                    rs.getInt("ma_nhan_vien") // Lấy giá trị mã nhân viên
                );
                hopDongs.add(hopDong); // Thêm hợp đồng vào danh sách
            }
        } catch (SQLException e) { // Bắt lỗi SQLException
            LOGGER.log(Level.SEVERE, "Error retrieving contract list", e); // Ghi log lỗi với mức độ SEVERE
            throw new RuntimeException("Error retrieving contract list", e); // Ném lỗi RuntimeException nếu có lỗi
        }
        return hopDongs; // Trả về danh sách hợp đồng
    }

    @Override
    public HopDong layHopDong(int soHopDong) { // Phương thức lấy thông tin một hợp đồng dựa trên số hợp đồng (ID)
        String sql = "SELECT * FROM hop_dong WHERE so_hop_dong = ?"; // Câu lệnh SQL để lấy hợp đồng theo số hợp đồng (ID)
        try (PreparedStatement stmt = connection.prepareStatement(sql)) { // Tạo đối tượng PreparedStatement để thực thi câu lệnh SQL
            stmt.setInt(1, soHopDong); // Đặt giá trị cho tham số số hợp đồng (ID)
            try (ResultSet rs = stmt.executeQuery()) { // Thực thi câu lệnh SQL và lấy kết quả
                if (rs.next()) { // Nếu có kết quả
                    return new HopDong(
                        rs.getInt("so_hop_dong"), // Lấy giá trị số hợp đồng (ID)
                        rs.getDate("ngay_bat_dau"), // Lấy giá trị ngày bắt đầu
                        rs.getDate("ngay_ket_thuc"), // Lấy giá trị ngày kết thúc
                        rs.getDate("ngay_ky"), // Lấy giá trị ngày ký
                        rs.getString("noi_dung"), // Lấy giá trị nội dung hợp đồng
                        rs.getInt("thoi_gian"), // Lấy giá trị thời gian
                        rs.getInt("ma_nhan_vien") // Lấy giá trị mã nhân viên
                    );
                }
            }
        } catch (SQLException e) { // Bắt lỗi SQLException
            LOGGER.log(Level.SEVERE, "Error retrieving contract", e); // Ghi log lỗi với mức độ SEVERE
            throw new RuntimeException("Error retrieving contract", e); // Ném lỗi RuntimeException nếu có lỗi
        }
        return null; // Trả về null nếu không tìm thấy hợp đồng
    }
}
