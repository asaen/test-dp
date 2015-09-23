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
package com.datapine.aop;

import com.jcabi.log.Logger;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Logging aspect.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 * @version $Id$
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * Logs an attempt to login by a user.
     * @param join Method UserDetailsServiceImpl#loadUserByUsername(...).
     * @checkstyle LineLengthCheck (2 lines)
     */
    @Before("execution(* com.datapine.service.impl.UserDetailsServiceImpl.loadUserByUsername(..))")
    public final void loginAttempt(final JoinPoint join) {
        Logger.info(this, "User '%s' tries to log in", join.getArgs()[0]);
    }

    /**
     * Logs a successful attempt to login by a user.
     * @param join Method
     *  SimpleUrlAuthenticationSuccessHandler#onAuthenticationSuccess(...).
     * @checkstyle LineLengthCheck (2 lines)
     */
    @After("execution(* org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler.onAuthenticationSuccess(..))")
    public final void loginSucceeded(final JoinPoint join) {
        final Authentication auth = (Authentication) join.getArgs()[2];
        Logger.info(
            this,
            "Login of the user '%s' succeeds with authorities %s",
            auth.getName(),
            auth.getAuthorities()
        );
    }

    /**
     * Logs an unsuccessful attempt to login by a user.
     * @param join Method
     *  SimpleUrlAuthenticationFailureHandler#onAuthenticationFailure(...).
     * @checkstyle LineLengthCheck (2 lines)
     */
    @After("execution(* org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler.onAuthenticationFailure(..))")
    public final void loginFailed(final JoinPoint join) {
        Logger.info(
            this,
            "Login of the user '%s' failed",
            ((HttpServletRequest) join.getArgs()[0])
                .getParameter("j_username")
        );
    }

}
