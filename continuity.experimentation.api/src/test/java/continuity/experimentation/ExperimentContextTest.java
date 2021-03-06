package continuity.experimentation;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.continuity.experimentation.Context;
import org.continuity.experimentation.Experiment;
import org.continuity.experimentation.action.ContextChange;
import org.continuity.experimentation.action.Delay;
import org.continuity.experimentation.exception.AbortException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Henning Schulz
 *
 */
public class ExperimentContextTest {

	private Context contextMock;

	private Path pathMock;

	private File fileMock;

	@Before
	public void setupMocks() throws IOException {
		pathMock = Mockito.mock(Path.class);
		Path rootPathMock = Mockito.mock(Path.class);
		fileMock = Mockito.mock(File.class);
		contextMock = Mockito.mock(Context.class);

		Mockito.when(contextMock.clone()).thenReturn(contextMock);

		Mockito.when(contextMock.toPath()).thenReturn(pathMock);
		Mockito.when(pathMock.toFile()).thenReturn(fileMock);

		Mockito.when(pathMock.getName(0)).thenReturn(rootPathMock);
		Mockito.when(rootPathMock.toFile()).thenReturn(fileMock);
		Mockito.when(rootPathMock.resolve("experiment.log")).thenReturn(rootPathMock);
		Mockito.when(rootPathMock.resolve("experiment.summary")).thenReturn(Files.createTempDirectory("ExperimentContextTest"));
	}

	@Test
	public void testLoop() throws AbortException {
		Experiment experiment = Experiment.newExperiment("loop-test").loop(5).append(new Delay(1)).endLoop().build();
		experiment.execute(contextMock);

		ArgumentCaptor<String> appendedCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> removedCaptor = ArgumentCaptor.forClass(String.class);

		Mockito.verify(contextMock, Mockito.times(1)).append(appendedCaptor.capture());
		Mockito.verify(contextMock, Mockito.times(5)).append(Mockito.any(), appendedCaptor.capture());
		assertThat(appendedCaptor.getAllValues()).containsExactly("loop-test", "iteration#1", "iteration#2", "iteration#3", "iteration#4", "iteration#5");

		Mockito.verify(contextMock, Mockito.times(6)).remove(removedCaptor.capture());
		assertThat(removedCaptor.getAllValues()).containsExactly("iteration#1", "iteration#2", "iteration#3", "iteration#4", "iteration#5", "loop-test");
	}

	@Test
	public void testConcurrent() throws AbortException {
		Experiment experiment = Experiment.newExperiment("concurrent-test").newThread().append(new Delay(1)).newThread().append(new Delay(1)).newThread().append(new Delay(1)).join().build();
		experiment.execute(contextMock);

		ArgumentCaptor<String> appendedCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> removedCaptor = ArgumentCaptor.forClass(String.class);

		Mockito.verify(contextMock, Mockito.times(1)).append(appendedCaptor.capture());
		Mockito.verify(contextMock, Mockito.times(3)).append(Mockito.any(), appendedCaptor.capture());
		assertThat(appendedCaptor.getAllValues()).containsExactlyInAnyOrder("concurrent-test", "thread#1", "thread#2", "thread#3");

		Mockito.verify(contextMock, Mockito.times(4)).remove(removedCaptor.capture());
		assertThat(removedCaptor.getAllValues()).containsExactlyInAnyOrder("thread#1", "thread#2", "thread#3", "concurrent-test");
	}

	@Test
	public void testCombination() throws AbortException {
		MockedContext context = new MockedContext(pathMock);
		ContextChange contextChange = new ContextChange("custom");

		Experiment experiment = Experiment.newExperiment("combined-test").loop(5).append(new Delay(1)).newThread().append(contextChange.append()).append(new Delay(1)).append(contextChange.remove())
				.newThread().append(new Delay(1)).join().endLoop().build();

		experiment.execute(context);


		assertThat(context.getCreatedPaths()).contains(createdPaths(5, 2));
		assertThat(context.getCreatedPaths()).containsOnly(createdPaths(5, 2));
	}

	private Path[] createdPaths(int numIterations, int numThreads) {
		List<Path> paths = new ArrayList<>();

		Path root = Paths.get("combined-test");
		paths.add(root);

		for (int i = 1; i <= numIterations; i++) {
			Path itPath = root.resolve("iteration#" + i);
			paths.add(itPath);

			for (int t = 1; t <= numThreads; t++) {
				Path threadPath = itPath.resolve("thread#" + t);
				paths.add(threadPath);

				if (t == 1) {
					paths.add(threadPath.resolve("custom"));
				}
			}
		}

		return paths.toArray(new Path[paths.size()]);
	}

}
