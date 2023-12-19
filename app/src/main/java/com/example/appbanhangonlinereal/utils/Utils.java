package com.example.appbanhangonlinereal.utils;

import com.example.appbanhangonlinereal.model.Admin;
import com.example.appbanhangonlinereal.model.GioHang;
import com.example.appbanhangonlinereal.model.User;

import java.util.List;

public class Utils {
    public static final String BASE_URL="http://10.45.36.31/banhang/";
    public static List<GioHang> manggiohang;
    public static User user_current = new User();
    public static Admin admin = new Admin();
}
