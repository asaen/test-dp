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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * Constant to locate an update view.
     */
    private static final String USERS_UPDATE_VIEW = "users/update";

    /**
     * Constant for user variable in jsp's.
     */
    private static final String USER_VAR = "user";

    /**
     * Injected service to communicate with the business layer.
     */
    @Autowired
    private transient UserService service;

    /**
     * Shows the list of users.
     * @return ModelAndView instance.
     */
    @RequestMapping(method = RequestMethod.GET)
    public final ModelAndView listUsers() {
        return new ModelAndView("users/users", "users", this.service.users());
    }

    /**
     * Redirects to the form for adding a user.
     * @return ModelAndView instance.
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public final ModelAndView addUser() {
        return new ModelAndView(
            USERS_UPDATE_VIEW,
            USER_VAR,
            new User()
        );
    }

    /**
     * Shows the details of the user.
     * @param uid The specified user id.
     * @return ModelAndView instance.
     */
    @RequestMapping("/show")
    public final ModelAndView showUser(
        @RequestParam(value = "id", required = true) final Long uid
    ) {
        return new ModelAndView("users/show", USER_VAR, this.service.user(uid));
    }

    /**
     * Shows update form for the specified user.
     * @param uid The specified user id.
     * @return ModelAndView instance.
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public final ModelAndView updateUser(
        @RequestParam(value = "id", required = true) final Long uid
    ) {
        return new ModelAndView(
            USERS_UPDATE_VIEW,
            USER_VAR,
            this.service.user(uid)
        );
    }

    /**
     * Updates the specified user.
     * @param user The user model attribute.
     * @param uid User ID.
     * @return ModelAndView instance.
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public final ModelAndView updateUser(
        @ModelAttribute("user") final User user,
        @RequestParam(value = "id", required = true) final Long uid
    ) {
        this.service.update(user);
        return this.listUsers();
    }

    /**
     * Removes the specified user.
     * @param user The specified user.
     * @return ModelAndView instance.
     */
    @RequestMapping("/delete")
    public final ModelAndView deleteUser(final User user) {
        this.service.delete(user);
        return this.listUsers();
    }

}
