package mmp.librarymanager;

import mmp.librarymanager.dto.BookReducedInstanceDTO;
import mmp.librarymanager.entities.Book;
import mmp.librarymanager.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PersistenceTests {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookInstanceRepository bookInstanceRepository;
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    ReaderRepository readerRepository;

    @Test
    public void contextLoads() {
        assertThat(authorRepository).isNotNull();
        assertThat(bookInstanceRepository).isNotNull();
        assertThat(operationRepository).isNotNull();
        assertThat(publisherRepository).isNotNull();
        assertThat(readerRepository).isNotNull();
    }

    @Test
    public void authorsNames() {
        int cnt = 0;
        for (String el: authorRepository.getAuthors()) {
            cnt += 1;
        }
        Assertions.assertEquals(cnt, 10);
    }

    @Test
    public void bookInstances() {
        for (int i = 0; i < 10; ++i) {
            Book b = bookRepository.findAll().get(i);

            Set<Long> all = bookInstanceRepository.findByBookISBN(b.getIsbn());
            Set<Long> notEnded = operationRepository.getNotEndedByBookISBN(b.getIsbn());

            assert(all.size() >= notEnded.size());
        }
    }


    @Test
    public void deleteReader() {
        Long id = 1L;
        readerRepository.deleteById(id);
        assertThat(readerRepository.getNamesById(id)).containsOnly();
        Pageable p = PageRequest.of(0, 1000);
        assertThat(operationRepository
                .getFiltered("", "", id + "!", "", "", false, p))
                .containsOnly();
    }

    @Test
    public void getBookTitles() {
        List<String> someTitles = List.of("Шелест страниц",
                "Неожиданность выбора",
                "Зачарованные мысли",
                "Мертвое море",
                "Неоновое поколение",
                "Лабиринт крови",
                "Эта вечная война",
                "Нити любви",
                "Сломанная кукла",
                "Голодный крик",
                "Пустые глаза",
                "Бесконечность возможностей",
                "Не одни",
                "Разбитые зеркала",
                "Песочный человек",
                "Порождение бездны",
                "Комната одиночества",
                "Ходячие кошмары",
                "Мастер веры в себя",
                "Не верь людям",
                "Ложные традиции верных слуг солнца",
                "Ядовитый питомец",
                "Мечтатель",
                "Храмы природы",
                "Чёрное небо",
                "Одна на двоих",
                "Осколки разбитого лета",
                "Дорога из лунного камня",
                "Фото с чердака",
                "Игры судьбы",
                "Билет в один конец",
                "Новогоднее чудо",
                "Вернись ко мне",
                "Неужели снова он?",
                "Улыбка будущего",
                "Убежать от реальности",
                "И сделать шаг",
                "Жёлтые одуванчики",
                "В поисках счастья",
                "Игры богов",
                "Драконье подвиги",
                "Одолжи мне счастье",
                "Осколки любви",
                "Там где я тебя нашёл",
                "Жестокая реальность",
                "Здесь и сейчас",
                "Академия нечистых адептов",
                "Свержение королевы морей",
                "Случайная встреча",
                "Копилка неудачника",
                "По ту сторону реки",
                "Цветы жизни",
                "Мост",
                "Монстр из снов младенцев",
                "Миры безумия",
                "Солнце которое не светит",
                "Мастер фантазий",
                "Северные ветра",
                "То, что хочется вернуть больше всего",
                "Маски человека",
                "Когда настанет тьма",
                "Громкая ложь",
                "Причина осмелеть",
                "Забытые чердаки",
                "Обитель желаний",
                "Молчание леса",
                "Далёкий путь",
                "Лес чудес",
                "Мысль во тьме",
                "Мороз по коже",
                "Клыкастые опасности северных областей",
                "Игра королей",
                "Ветер с моря",
                "Один шаг назад",
                "Звенящая пошлость",
                "Маленькая фантазия",
                "Крик в пустоту",
                "Город иных",
                "Чёрная полоса",
                "Белый лев",
                "Путь к звезде",
                "Моя вечная зима",
                "С тобой или без тебя",
                "Пир для голодных",
                "Алые цветы смерти",
                "Бесконечный мир",
                "Сладкая вишня",
                "Мы невозможны...",
                "Звездный шаг",
                "Пламя ночи",
                "Пульсация",
                "Тёмное подземелье демонов",
                "Полёт на хвосте дракона",
                "Твои несбыточные мечты",
                "Ангелы не спят",
                "Дом вечных фантазий",
                "Я иду искать");
        assertThat(bookRepository.getTitles())
                .containsAll(someTitles);
    }

    @Test
    public void getPublishers() {
        List<String> allPublishers = List.of(
                "Владос",
                "АСТ",
                "URSS.ru",
                "ГРАНД-ФАИР",
                "Интеллект",
                "ИНФРА-М",
                "ОНИКС",
                "Питер",
                "КноРус",
                "Академия",
                "Айрис-пресс",
                "АкадемическийПроект",
                "ВесьМир",
                "Вече",
                "Высшаяшкола",
                "Дрофа",
                "КолосС",
                "Лань",
                "Омега-Л",
                "Просвещение",
                "Проспект",
                "Профессия",
                "Флинта",
                "Эксмо",
                "Академкнига",
                "Вузовскаякнига"
        );
        assertThat(publisherRepository.getPublishers())
                .containsExactlyInAnyOrderElementsOf(allPublishers);
    }

    @Test
    public void getFilteredReaders() {
        Pageable p = PageRequest.of(0, 1000);
        assertThat(readerRepository.getFiltered("Мих", "47", p))
                .allMatch(r -> Objects.equals(r.getName(), "Дмитриев Михаил Мэлсович"))
                .hasSize(1);
    }

    @Test
    public void getFilteredReadersNoFilter() {
        int cnt = 32;
        Pageable p = PageRequest.of(0, cnt);
        assertThat(readerRepository.getFiltered("", "", p))
                .hasSize(cnt);
    }

    @Test
    public void getReaderNamesById() {
        assertThat(readerRepository.getNamesById(4301L))
                .hasSize(1)
                .allMatch(r -> Objects.equals(r, "Корнилова Томила Платоновна"));
    }

    @Test
    public void getReaderNames() {
        assertThat(readerRepository.getReaderNames())
                .hasSize(77);
    }

    @Test
    public void getReaderIds() {
        assertThat(readerRepository.getReaderIds())
                .hasSize(100);
    }


    @Test
    public void getOperationsFiltered() {
        Pageable p = PageRequest.of(0, 500);
        assertThat(operationRepository.getFiltered("978",
                "оТ", "1", "в",
                "", true, p))
                .hasSize(2);
    }

    @Test
    public void getBookInfo() {
        Pageable p = PageRequest.of(0, 500);
        assertThat(bookRepository.getBookInfo("А", "В", "9788", p))
                .hasSize(4);
    }

    @Test
    public void getBookIsbns() {
        assertThat(bookRepository.getIsbns())
                .hasSize(444)
                .allMatch(isbn -> isbn.matches("978[0-9]{10}"));
    }

    @Test
    public void getBookInstancesReduced() {
        assertThat(bookInstanceRepository.getReducedInfoById(51L))
                .containsOnly(new BookReducedInstanceDTO("Калинин Кондратий Тимофеевич",
                        "Алая роза"))
                .hasSize(1);
    }

}