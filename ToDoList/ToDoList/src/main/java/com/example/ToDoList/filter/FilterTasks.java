package com.example.ToDoList.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;

import at.favre.lib.crypto.bcrypt.BCrypt;

import com.example.ToDoList.users.IUsers;
import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTasks extends OncePerRequestFilter {
    @Autowired
    IUsers users;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var pathFilter = request.getServletPath();

        if (pathFilter.startsWith("/tasks")) {
            var auth = request.getHeader("Authorization");
            var authInfo = auth.substring("Basic".length()).trim();

            byte[] authByte = Base64.getDecoder().decode(authInfo);
            var authString = new String(authByte);

            String[] authArrayString = authString.split(":");
            var authUser = authArrayString[0];
            var authPassword = authArrayString[1];

            var user = users.findByUsername(authUser);
            if (user == null) {
                response.sendError(401);
            } else {
                var password = BCrypt.verifyer().verify(authPassword.toCharArray(), user.getPassword());

                if (password.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
