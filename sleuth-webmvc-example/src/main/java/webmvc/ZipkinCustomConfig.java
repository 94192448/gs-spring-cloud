package webmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.Call;
import zipkin2.Span;
import zipkin2.codec.Encoding;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;

import java.util.List;

/**
 * The class description.
 *
 * @author yangzq80@gmail.com
 * @date 2019-11-25
 * @see
 * @since 1.0.0
 */
@Configuration
@Slf4j
public class ZipkinCustomConfig {

   //@Bean(ZipkinAutoConfiguration.REPORTER_BEAN_NAME)
    @Bean("reporter")
    Reporter<Span> myReporter() {
        return AsyncReporter.create(mySender());
    }

    //@Bean(ZipkinAutoConfiguration.SENDER_BEAN_NAME)
    @Bean("sender")
    MySender mySender() {

        return new MySender();
    }

    static class MySender extends Sender {

        private boolean spanSent = false;

        boolean isSpanSent() {
            return this.spanSent;
        }

        @Override
        public Encoding encoding() {
            return Encoding.JSON;
        }

        @Override
        public int messageMaxBytes() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int messageSizeInBytes(List<byte[]> encodedSpans) {
            return encoding().listSizeInBytes(encodedSpans);
        }

        @Override
        public Call<Void> sendSpans(List<byte[]> encodedSpans) {
            for(byte[] encodeSpan:encodedSpans){

                log.info(" the zipkin data--->{}",new String(encodeSpan));
            }

            this.spanSent = true;
            return Call.create(null);
        }

    }

}
