package br.ce.wcaquino;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelRunner extends BlockJUnit4ClassRunner  {
    public ParallelRunner(Class<?> klass) throws InitializationError {
        super(klass);
        setScheduler(new ThreadPoll());
    }

    private static class ThreadPoll implements RunnerScheduler {

        private final ExecutorService executorService = Executors.newFixedThreadPool(5);

        @Override
        public void schedule(Runnable runnable) {
            executorService.submit(runnable);
        }

        @Override
        public void finished() {
            executorService.shutdown();
        }
    }
}
