package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionMapper permissionMapper;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "admin/role/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("role", new Role());
        model.addAttribute("permissions", permissionMapper.findAll());
        return "admin/role/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Role role, RedirectAttributes redirectAttributes) {
        roleService.create(role);
        redirectAttributes.addFlashAttribute("message", "角色创建成功");
        return "redirect:/admin/roles";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("role", roleService.findById(id));
        model.addAttribute("permissions", permissionMapper.findAll());
        return "admin/role/form";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Role role, RedirectAttributes redirectAttributes) {
        role.setId(id);
        roleService.update(role);
        redirectAttributes.addFlashAttribute("message", "角色更新成功");
        return "redirect:/admin/roles";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        roleService.delete(id);
        redirectAttributes.addFlashAttribute("message", "角色删除成功");
        return "redirect:/admin/roles";
    }

    @PostMapping("/{roleId}/permissions/{permissionId}")
    @ResponseBody
    public String togglePermission(@PathVariable Long roleId, @PathVariable Long permissionId,
            @RequestParam boolean assigned) {
        if (assigned) {
            roleService.assignPermission(roleId, permissionId);
            return "权限已分配";
        } else {
            roleService.removePermission(roleId, permissionId);
            return "权限已移除";
        }
    }
}