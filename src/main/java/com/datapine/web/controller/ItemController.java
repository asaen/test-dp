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

import com.datapine.domain.Item;
import com.datapine.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Item controller.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@Controller
@RequestMapping("/items")
public class ItemController {

    /**
     * Constant for item variable in jsp's.
     */
    private static final String ITEM_VAR = "item";

    /**
     * Injected service to communicate with the business layer.
     */
    @Autowired
    private transient ItemService service;

    /**
     * Shows the list of items.
     * @return ModelAndView instance.
     */
    @RequestMapping(method = RequestMethod.GET)
    public final ModelAndView items() {
        return new ModelAndView("items/items", "items", this.service.items());
    }

    /**
     * Redirects to the form for adding an item.
     * @return ModelAndView instance.
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public final ModelAndView add() {
        return new ModelAndView("items/add", ITEM_VAR, new Item());
    }

    /**
     * Adds a new item.
     * @param item New item.
     * @return ModelAndView instance.
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public final ModelAndView add(@ModelAttribute(ITEM_VAR) final Item item) {
        this.service.update(item);
        return this.items();
    }

    /**
     * Removes the specified item.
     * @param item The specified item.
     * @return ModelAndView instance.
     */
    @RequestMapping("/delete")
    public final ModelAndView delete(final Item item) {
        this.service.delete(item);
        return this.items();
    }

}
