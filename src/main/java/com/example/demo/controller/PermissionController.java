package com.example.demo.controller;

import com.example.demo.entity.Permission;
import com.example.demo.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/permissions")
@PreAuthorize("hasRole('ADMIN')")
public class PermissionController {

    @Autowired
    private PermissionMapper permissionMapper;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("permissions", permissionMapper.findAll());
        return "admin/permission/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("permission", new Permission());
        return "admin/permission/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Permission permission, RedirectAttributes redirectAttributes) {
        permissionMapper.insert(permission);
        redirectAttributes.addFlashAttribute("message", "权限创建成功");
        return "redirect:/admin/permissions";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("permission", permissionMapper.findById(id));
        return "admin/permission/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Permission permission,
            RedirectAttributes redirectAttributes) {
        permission.setId(id);
        permissionMapper.update(permission);
        redirectAttributes.addFlashAttribute("message", "权限更新成功");
        return "redirect:/admin/permissions";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        permissionMapper.delete(id);
        redirectAttributes.addFlashAttribute("message", "权限删除成功");
        return "redirect:/admin/permissions";
    }
}