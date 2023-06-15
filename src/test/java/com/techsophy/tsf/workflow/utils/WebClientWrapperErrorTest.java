//package com.techsophy.tsf.workflow.utils;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.reactive.function.client.ClientResponse;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.function.Function;
//import java.util.function.Predicate;
//
//import static org.junit.Assert.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class WebClientWrapperErrorTest {
//    @Mock
//    private WebClient webClientMock;
//
//    @InjectMocks
//    WebClientWrapper webClientWrapper;
//
//    @Mock
//    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;
//
//    @Mock
//    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
//
//    @Mock
//    private CustomMinimalForTestResponseSpec responseSpecMock;
//
//    @Test
//    void shouldFailsWhenHttpStatus5xx() {
//        //given
//        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
//        when(requestHeadersUriSpecMock.uri(any(Function.class)))
//                .thenReturn(requestHeadersSpecMock);
//        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
//
//        when(responseSpecMock.getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
//
//        when(responseSpecMock.onStatus(any(Predicate.class), any(Function.class))).thenCallRealMethod();
//
//        //when + Then
////        assertThrows(MyCustomException.class,
////                () -> experimentalWebClient.doStuff(),
////                "call fails with Internal Server Error");
//    }
//
//
//    abstract class CustomMinimalForTestResponseSpec implements WebClient.ResponseSpec {
//
//        public abstract HttpStatus getStatus();
//
//        public WebClient.ResponseSpec onStatus(Predicate<HttpStatus> statusPredicate, Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction) {
//            if (statusPredicate.test(this.getStatus()))
//                exceptionFunction.apply(ClientResponse.create(HttpStatus.OK).build()).block();
//            return this;
//        }
//
//    }
//}
//
