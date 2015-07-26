package me.vadyalex.petproject.service.configuration;

import javax.servlet.annotation.WebFilter;

/**
 * Created by vadyalex.
 */
@WebFilter("/*")
public class GuiceFilter extends com.google.inject.servlet.GuiceFilter {

}
