package guru.springframework.repositories.reactive;

import guru.springframework.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReactiveUnitOfMeasureRepositoryTest {
    @Autowired
    private CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSaveCategory() {
        Category asian = new Category();
        asian.setDescription("Part of asian cousin.");

        categoryReactiveRepository.save(asian).block();

        Long result = categoryReactiveRepository.count().block();
        assertEquals(Long.valueOf(1L), result);
    }

    @Test
    public void testFindByDescription() {
        Category asian = new Category();
        asian.setDescription("Asian");

        categoryReactiveRepository.save(asian).then().block();

        Category result = categoryReactiveRepository.findByDescription("Asian").block();
        assertNotNull(result.getId());
    }
}
