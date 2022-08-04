package com.techsophy.tsf.workflow.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import static com.techsophy.tsf.workflow.constants.WorkflowTestConstants.TEST_ACTIVE_PROFILE;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ActiveProfiles(TEST_ACTIVE_PROFILE)
@SpringBootTest
class LocaleConfigTest
{
    @Mock
    HttpServletRequest mockHttpServletRequest;
    @Mock
    List<Locale> mockLocales;
    @Mock
    List<Locale.LanguageRange> mockList;
    @InjectMocks
    LocaleConfig mockLocaleConfig;

    @Test
     void resolveLocaleTest()
    {
        when(mockLocaleConfig.resolveLocale(mockHttpServletRequest)).thenReturn(any());
       Locale responseTest= mockLocaleConfig.resolveLocale(mockHttpServletRequest);
        Assertions.assertNotNull(responseTest);
    }
}
