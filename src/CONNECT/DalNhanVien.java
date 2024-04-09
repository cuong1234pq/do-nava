/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONNECT;

import MODEL.NhanVien;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
import java.sql.Date;

/**
 *
 * @author CUONG
 */
public class DalNhanVien extends DAOCONTENT {

    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT [id]\n"
                + "      ,[HoTen]\n"
                + "      ,[NgaySinh]\n"
                + "      ,[DiaChi]\n"
                + "      ,[SDT]\n"
                + "      ,[CMND]\n"
                + "  FROM [dbo].[NhanVien]";
        try {

            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                NhanVien c = new NhanVien(rs.getString("id"), rs.getString("HoTen"),
                        rs.getString("ngaysinh"), rs.getString("diachi"),
                        rs.getString("sdt"), rs.getString("cmnd")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;
    }

    public int insert(NhanVien nv) throws ParseException {
        int i = 0;

        String sql = "INSERT INTO [dbo].[NhanVien]\n"
                + "           ([id]\n"
                + "           ,[HoTen]\n"
                + "           ,[NgaySinh]\n"
                + "           ,[DiaChi]\n"
                + "           ,[SDT]\n"
                + "           ,[CMND])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse(nv.getNgaysinh());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            // Bây giờ bạn có thể sử dụng đối tượng "sqlDate" trong câu lệnh SQL của mình
            System.out.println(sqlDate);

            st.setString(1, nv.getId());
            st.setString(2, nv.getHoTen());
            st.setDate(3, sqlDate);
            st.setString(4, nv.getDiachi());
            st.setString(5, nv.getSdt());
            st.setString(6, nv.getCmnd());

            st.executeUpdate();
            i = 0;

        } catch (SQLException ex) {
            String errorMessage = ex.getMessage();

            if (errorMessage.contains("FOREIGN KEY constraint")) {
                i = 1;
            } else if (errorMessage.contains("PRIMARY KEY constraint")) {
                System.out.println("pro");
                i = 2;
                System.out.println(i);
                System.out.println(errorMessage);

            }
        }
        return i;

    }

    public void delete(NhanVien nv) {
        String sql = "DELETE FROM [dbo].[NhanVien]\n"
                + "      WHERE HOTEN = '" + nv.getHoTen() + "'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

//        String sql =  "update nhanvien " 
//                + "set "
//                + "id = '" + nv.getId()+ "',"
//                + "hoten = '" + nv.getHoTen()+ "', "
//                + "ngaysinh = '" + nv.getNgaysinh()+ "', "
//                + "daichi = '" + nv.getDiachi()+ "', "
//                + "SDT = '" + nv.getSdt()+ "' "
//                + "CMND = '" + nv.getCmnd()+ "' "
//                
//                + "where hoten = '" + nv.getHoTen();
    public void update(NhanVien cuss) throws ParseException {

        String sql = "UPDATE [dbo].[NhanVien]\n"
                + "   SET [id] = ?\n"
                + "      ,[HoTen] = ?\n"
                + "      ,[NgaySinh] =?\n"
                + "      ,[DiaChi] = ?\n"
                + "      ,[SDT] = ?\n"
                + "      ,[CMND] = ?\n"
                + " WHERE id = '"+ cuss.getId()+"'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse(cuss.getNgaysinh());
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            System.out.println(sqlDate);
            st.setString(1, cuss.getId());
            st.setString(2, cuss.getHoTen());
            st.setDate(3, sqlDate);
            st.setString(4, cuss.getDiachi());
            st.setString(5, cuss.getSdt());
           
            st.setString(6,cuss.getCmnd());

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<NhanVien> finCustomersByName(NhanVien c) {
        List<NhanVien> list = new ArrayList<>();
        String sql = "select * from nhanvien where NHANVIEN.HOTEN like '%" + c.getHoTen() + "%'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                c = new NhanVien(rs.getString("id"), rs.getString("HOTEN"),
                        rs.getString("NGAYSINH"), rs.getString("DIACHI"),
                        rs.getString("SDT"), rs.getString("CMND")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return list;

    }

}
