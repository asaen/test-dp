/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Alexey Saenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.datapine.web.controller;

import com.datapine.domain.User;
import com.datapine.service.UserService;
import com.jcabi.log.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * User controller.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@Controller
@RequestMapping("/users")
public class UserController {

    /**
     * Injected service to communicate with the business layer.
     */
    @Autowired
    private transient UserService service;

    @PostConstruct
    public final void init() {
        this.service.register("admin@dp.com", "admin");
        Logger.debug(this, "Init method executed.");
    }

    /**
     * Shows the list of users.
     * @return ModelAndView instance.
     */
    @RequestMapping("/")
    public final ModelAndView listUsers() {
        return new ModelAndView("users/users", "users", this.service.users());
    }

    /**
     * Adds a user.
     * @param email Email.
     * @param password Password.
     * @return ModelAndView instance.
     */
    @RequestMapping("/add")
    public final ModelAndView addUser(
        final String email,
        final String password
    ) {
        this.service.register(email, password);
        return null;
    }

    /**
     * Shows the details of the user.
     * @return ModelAndView instance.
     */
    @RequestMapping("/show")
    public final ModelAndView showUser(
        @RequestParam(value = "id", required = true) final Long uid
    ) {
        return new ModelAndView("users/show", "user", this.service.user(uid));
    }

    /**
     * Updates the specified user.
     * @param userid User ID.
     * @param oldpwd Old password.
     * @param newpwd New password.
     * @return ModelAndView instance.
     */
    @RequestMapping("/update")
    public final ModelAndView updateUser(final Long userid, final String oldpwd,
        final String newpwd) {
        this.service.updatePassword(userid, oldpwd, newpwd);
        return null;
    }

    /**
     * Removes the specified user.
     * @param user The specified user.
     * @return ModelAndView instance.
     */
    @RequestMapping("/delete")
    public final ModelAndView deleteUser(final User user) {
        this.service.delete(user);
        return null;
    }

}
