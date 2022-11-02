package hu.webuni.cst.kamarasd.web;

import java.lang.reflect.Method;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.querydsl.core.types.Predicate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResolverHelper {
	
	private final PageableHandlerMethodArgumentResolver pageableResolver;
	private final QuerydslPredicateArgumentResolver predicateResolver;
	
	public Pageable createPageable(Class<?> myClass, String pageableConfigurerMethodName, NativeWebRequest nWebRequest) {
		Method method;
		
		try {
			method = myClass.getMethod(pageableConfigurerMethodName, Pageable.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e);			
		}
		MethodParameter mParameter = new MethodParameter(method, 0);
		ModelAndViewContainer mContainer = null;
		WebDataBinderFactory wFactory = null;
		Pageable pageable = pageableResolver.resolveArgument(mParameter, mContainer, nWebRequest, wFactory);
		return pageable;
		
	}
	
	public Predicate createPredicate(Class<?> myClass, String configMethodeName, NativeWebRequest nWebRequest) {
		Method method;
		
		try {
			method = myClass.getMethod(configMethodeName, Predicate.class);
			MethodParameter mParameter = new MethodParameter(method, 0);
			ModelAndViewContainer mContainer = null;
			WebDataBinderFactory wFactory = null;
			return (Predicate) predicateResolver.resolveArgument(mParameter, mContainer, nWebRequest, wFactory);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
