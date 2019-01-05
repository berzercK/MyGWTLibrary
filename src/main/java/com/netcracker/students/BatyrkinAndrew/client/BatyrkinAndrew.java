package com.netcracker.students.BatyrkinAndrew.client;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;
import com.netcracker.students.BatyrkinAndrew.client.services.*;
import com.netcracker.students.BatyrkinAndrew.shared.bean.MyBook;
import com.netcracker.students.BatyrkinAndrew.shared.bean.entity.BookType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */

public class BatyrkinAndrew implements EntryPoint {

    private static CellTable<MyBook> cellTable;
    private static ArrayList<MyBook> myBookList;
    private static SimplePager pager;
//    private final CwConstants constants;

    private GetTableServiceAsync getTableService = GWT.create(GetTableService.class);

    private VerticalPanel vpMain = new VerticalPanel();
    private VerticalPanel vpBut = new VerticalPanel();
    private HorizontalPanel hpAdded = new HorizontalPanel();
    private HorizontalPanel hpMain = new HorizontalPanel();
    private Button bAdd =        new Button("Добавить книгу");
    private Button bDelete =     new Button("Удалить книгу");
    private Button sortByTitle = new Button("Сорт. по названию");

    private TextBox tbId = new TextBox();
    private TextBox tbAuthor = new TextBox();
    private TextBox tbTitle = new TextBox();
    private TextBox tbPageAmount = new TextBox();
    private TextBox tbDateOfPublic = new TextBox();
    private ListBox lbType = new ListBox();
    private TextBox tbCount = new TextBox();



    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        getTableService.getListOfBook(new TableCallBack());

        // Do not refresh the headers and footers every time the data is updated.
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);

        // Attach a column sort handler to the ListDataProvider to sort the list.
        ColumnSortEvent.ListHandler<MyBook> sortHandler = new ColumnSortEvent.ListHandler<>(myBookList);
        cellTable.addColumnSortHandler(sortHandler);

        // Create a Pager to control the table.
        SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
        pager = new SimplePager(SimplePager.TextLocation.CENTER, pagerResources, false, 0, true);
        pager.setDisplay(cellTable);

        // Add a selection model so we can select cells.
        final SelectionModel<MyBook> selectionModel = new MultiSelectionModel<>(
                MyBook::getId);
        cellTable.setSelectionModel(selectionModel,
                DefaultSelectionEventManager.<MyBook> createCheckboxManager());

        initTableColumns(selectionModel, sortHandler);

        bAdd.addClickHandler(event -> {
            try {
                int id = Integer.parseInt(tbId.getText());
                String author = tbAuthor.getText();
                String title = tbTitle.getText();
                int pageAmount = Integer.parseInt(tbPageAmount.getText());
                String deteOfPub = tbDateOfPublic.getText();
                BookType bookType = BookType.valueOf(lbType.getSelectedItemText());
                int count = Integer.parseInt(tbCount.getText());
                final MyBook newMyBook = new MyBook(id, author, title, pageAmount, deteOfPub, new Date(), bookType, count);

                AddNewBookServiceAsync addNewBookService = GWT.create(AddNewBookService.class);
                addNewBookService.addNewBook(newMyBook, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Что-то пошло не так!");
                    }

                    @Override
                    public void onSuccess(Void result) {
                        myBookList.add(newMyBook);
                        cellTable.removeFromParent();
                        createTable();
                        createPanel();
                    }
                });
            } catch (Exception e) {
                Window.alert("Некорректный ввод данных!");
            }
        });

        bDelete.addClickHandler(event -> {
//            selectionModel = new SingleSelectionModel<>();
//            cellTable.setSelectionModel(selectionModel);
            selectionModel.addSelectionChangeHandler(event1 -> {
                MyBook selectedMyBook = (MyBook) ((MultiSelectionModel<MyBook>) selectionModel).getSelectedSet();
                if (selectedMyBook != null) {
                    final MyBook deletedMyBook = selectedMyBook;
                    DeleteBookServiceAsync deleteBookService = GWT.create((DeleteBookService.class));
                    deleteBookService.deleteBook(deletedMyBook, new AsyncCallback<Void>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            Window.alert("Что-то пошло не так!");
                        }

                        @Override
                        public void onSuccess(Void result) {
                            myBookList.remove(deletedMyBook);
                            cellTable.removeFromParent();
                            createTable();
                            createPanel();
                        }
                    });
                }
            });
        });

        sortByTitle.addClickHandler(event -> {
            SortByTitleServiceAsync sortByTitleService = GWT.create(SortByTitleService.class);
            sortByTitleService.sortByTitle(myBookList, new AsyncCallback<ArrayList<MyBook>>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Что-то пошло не так!");
                }

                @Override
                public void onSuccess(ArrayList<MyBook> result) {
                    cellTable.removeFromParent();
                    myBookList = result;
                    createTable();
                    createPanel();
                }
            });
        });

        createPanel();
    }


    private void createTable() {
        cellTable = new CellTable<>(myBookList.size());
        ColumnSortEvent.ListHandler<MyBook> sortHandler = new ColumnSortEvent.ListHandler<>(myBookList);
        cellTable.addColumnSortHandler(sortHandler);

        addIdColumn(cellTable, sortHandler);
        addAuthorColumn(cellTable, sortHandler);
        addTitle(cellTable);
        addPageAmountColumn(cellTable);

        addSetTypeColumn(cellTable);
        addCountColumn(cellTable);

        addDateOfPublicColumn(cellTable);
        addDateOfAddedColumn(cellTable);

        cellTable.setWidth("100%", true);

        cellTable.setRowCount(myBookList.size(), true);
        cellTable.setRowData(0, myBookList);

    }

    private void addCountColumn(CellTable<MyBook> cellTable) {
        TextColumn<MyBook> count = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) {
                return String.valueOf(object.getCount());
            }
        };
        cellTable.addColumn(count, "Количество");
    }

    private void addSetTypeColumn(CellTable<MyBook> cellTable) {
        TextColumn<MyBook> type = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) {
                return String.valueOf(object.getTypeEnum());
            }
        };
        cellTable.addColumn(type, "Тип книги");

    }

    private void addDateOfAddedColumn(CellTable<MyBook> cellTable) {
        TextColumn<MyBook> dateOfAdded = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) { return String.valueOf(object.getDateOfAdded()); }
        };
        cellTable.addColumn(dateOfAdded, "Дата добавления");
    }

    private void addDateOfPublicColumn(CellTable<MyBook> cellTable) {
        TextColumn<MyBook> dateOfPublic = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) { return String.valueOf(object.getDateOfPublication()); }
        };
        cellTable.addColumn(dateOfPublic, "Дата публикации");
    }

    private void addPageAmountColumn(CellTable<MyBook> cellTable) {
        TextColumn<MyBook> pageAmount = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) {
                return String.valueOf(object.getPageAmount());
            }
        };
        cellTable.addColumn(pageAmount, "Страниц");
    }

    private void addTitle(CellTable<MyBook> cellTable) {
        TextColumn<MyBook> title = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) {
                return String.valueOf(object.getTitle());
            }
        };
        cellTable.addColumn(title, "Название");
        title.setSortable(true);

        ColumnSortEvent.ListHandler<MyBook> titleSortHandler = new ColumnSortEvent.ListHandler<>(myBookList);
        titleSortHandler.setComparator(title, MyBook::compareTitle);
        cellTable.addColumnSortHandler(titleSortHandler);

    }

    private void addAuthorColumn(CellTable<MyBook> cellTable, ColumnSortEvent.ListHandler<MyBook> sortHandler) {
        TextColumn<MyBook> author = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) {
                return String.valueOf(object.getAuthor());
            }
        };
        cellTable.addColumn(author, "Автор");
        author.setSortable(true);

        sortHandler.setComparator(author, Comparator.comparing(MyBook::getAuthor));
        cellTable.getColumnSortList().push(author);
        cellTable.getColumnSortList().push(author);

//        ColumnSortEvent.ListHandler<MyBook> authorSortHandler = new ColumnSortEvent.ListHandler<>(myBookList);
//        authorSortHandler.setComparator(author, MyBook::compareTo);
//        cellTable.addColumnSortHandler(authorSortHandler);


    }

    private void addIdColumn(CellTable<MyBook> cellTable, ColumnSortEvent.ListHandler<MyBook> sortHandler) {
        TextColumn<MyBook> id = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) {
                return String.valueOf(object.getId());
            }
        };
        cellTable.addColumn(id, "id");
        id.setSortable(true);

        sortHandler.setComparator(id, (o1, o2) -> {
            if (o1.getId() - o2.getId() == 0) return 0;
            return o1.getId() - o2.getId();
        });
        cellTable.getColumnSortList().push(id);
        cellTable.getColumnSortList().push(id);

        // Add a ColumnSortEvent.ListHandler to connect sorting to the
        // java.util.List.
//        ColumnSortEvent.ListHandler<MyBook> idSortHandler = new ColumnSortEvent.ListHandler<>(myBookList);
//        idSortHandler.setComparator(id, MyBook::compareId);
//        cellTable.addColumnSortHandler(idSortHandler);
//        // We know that the data is sorted alphabetically by default.
//        cellTable.getColumnSortList().push(id);
//        cellTable.getColumnSortList().push(id);

    }

    private void createPanel() {
        tbId.setText(String.valueOf(myBookList.get(myBookList.size() - 1).getId() + 1));
        tbId.setMaxLength(5);

        tbAuthor.setText("Автор");
        tbTitle.setText("Название");
        tbPageAmount.setText("Страниц");
        tbDateOfPublic.setText("Дата публикации");
        for (BookType bt :
                BookType.values()) {
            lbType.addItem(String.valueOf(bt));
        }
        tbCount.setText("Количество в наличии");

        hpAdded.add(tbId);
        hpAdded.add(tbAuthor);
        hpAdded.add(tbTitle);
        hpAdded.add(tbPageAmount);
        hpAdded.add(lbType);
        hpAdded.add(tbCount);
        hpAdded.add(tbDateOfPublic);

        vpBut.add(bAdd);
        vpBut.add(bDelete);
        vpBut.add(sortByTitle);

        vpMain.add(cellTable);
        vpMain.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_JUSTIFY);
        vpMain.add(hpAdded);

        hpMain.add(vpMain);
        hpMain.add(vpBut);

        RootPanel.get("container").add(hpMain);
    }


    private class TableCallBack implements AsyncCallback<ArrayList<MyBook>> {
        @Override
        public void onFailure(Throwable caught) {
            Window.alert("Сервер недоступен: " + caught.getMessage());
        }

        @Override
        public void onSuccess(ArrayList<MyBook> result) {
            myBookList = result;
            createTable();
            createPanel();

//
//
//            firstNameColumn.setSortable(true);
//            sortHandler.setComparator(firstNameColumn, new Comparator<ContactInfo>() {
//                @Override
//                public int compare(ContactInfo o1, ContactInfo o2) {
//                    return o1.getFirstName().compareTo(o2.getFirstName());
//                }
//            });

        }
    }
    /**
     * Add the columns to the table.
     * @param selectionModel
     * @param sortHandler
     */
    private void initTableColumns(
            final SelectionModel<MyBook> selectionModel,
            ColumnSortEvent.ListHandler<MyBook> sortHandler) {
        // Checkbox column. This table will uses a checkbox column for selection.
        // Alternatively, you can call cellTable.setSelectionEnabled(true) to enable
        // mouse selection.
        Column<MyBook, Boolean> checkColumn = new Column<MyBook, Boolean>(
                new CheckboxCell(true, false)) {
            @Override
            public Boolean getValue(MyBook object) {
                // Get the value from the selection model.
                return selectionModel.isSelected(object);
            }
        };
        cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br/>"));
        cellTable.setColumnWidth(checkColumn, 40, Style.Unit.PX);

        // First name.
        Column<MyBook, String> authorColumn = new Column<MyBook, String>(
                new EditTextCell()) {
            @Override
            public String getValue(MyBook object) {
                return object.getAuthor();
            }
        };
        authorColumn.setSortable(true);
        sortHandler.setComparator(authorColumn, Comparator.comparing(MyBook::getAuthor));
        cellTable.addColumn(authorColumn);
        cellTable.setColumnWidth(authorColumn, 20, Style.Unit.PCT);

        // Last name.
        Column<MyBook, String> titleColumn = new Column<MyBook, String>(
                new EditTextCell()) {
            @Override
            public String getValue(MyBook object) {
                return object.getTitle();
            }
        };
        titleColumn.setSortable(true);
        sortHandler.setComparator(titleColumn, Comparator.comparing(MyBook::getTitle));
        cellTable.addColumn(titleColumn);
        cellTable.setColumnWidth(titleColumn, 20, Style.Unit.PCT);

        // Address.
        Column<MyBook, String> pageAmountColumn = new Column<MyBook, String>(
                new TextCell()) {
            @Override
            public String getValue(MyBook object) {
                return String.valueOf(object.getPageAmount());
            }
        };
        pageAmountColumn.setSortable(true);
        pageAmountColumn.setDefaultSortAscending(false);
        sortHandler.setComparator(pageAmountColumn, MyBook::compareId);
        cellTable.addColumn(pageAmountColumn);

        cellTable.setColumnWidth(pageAmountColumn, 60, Style.Unit.PCT);
    }

}
