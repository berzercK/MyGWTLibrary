package com.netcracker.students.BatyrkinAndrew.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.netcracker.students.BatyrkinAndrew.client.services.*;
import com.netcracker.students.BatyrkinAndrew.shared.MyBook;
import com.netcracker.students.BatyrkinAndrew.shared.BookType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */

public class BatyrkinAndrew implements EntryPoint {

    private static CellTable<MyBook> cellTable;
    private static ArrayList<MyBook> myBookList;
    private ColumnSortEvent.ListHandler<MyBook> sortHandler;

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
            SingleSelectionModel<MyBook> selectionModel = new SingleSelectionModel<>();
            cellTable.setSelectionModel(selectionModel);
            selectionModel.addSelectionChangeHandler(event1 -> {
                MyBook selectedMyBook = selectionModel.getSelectedObject();
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

    }


    private void createTable() {
        cellTable = new CellTable<>(myBookList.size());
//         Do not refresh the headers and footers every time the data is updated.
        cellTable.setAutoHeaderRefreshDisabled(true);
        cellTable.setAutoFooterRefreshDisabled(true);

        // Attach a column sort handler to the ListDataProvider to sort the list.
        sortHandler = new ColumnSortEvent.ListHandler<>(myBookList);

        TextColumn<MyBook> id = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) {
                return String.valueOf(object.getId());
            }
        };
        cellTable.addColumn(id, "id");
        id.setSortable(true);

        TextColumn<MyBook> author = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) {
                return String.valueOf(object.getAuthor());
            }
        };
        cellTable.addColumn(author, "Автор");
        author.setSortable(true);
//        sortHandler.setComparator(author, Comparator.comparing(MyBook::getAuthor));
//        addAuthorColumn(cellTable, sortHandler);

        TextColumn<MyBook> title = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) {
                return String.valueOf(object.getTitle());
            }
        };
        cellTable.addColumn(title, "Название");
        title.setSortable(true);
//        sortHandler.setComparator(title, MyBook::compareTitle);
//        addTitle(cellTable);

        TextColumn<MyBook> pageAmount = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) {
                return String.valueOf(object.getPageAmount());
            }
        };
        cellTable.addColumn(pageAmount, "Страниц");
        pageAmount.setSortable(true);
//        addPageAmountColumn(cellTable);

        TextColumn<MyBook> type = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) {
                return String.valueOf(object.getTypeEnum());
            }
        };
        cellTable.addColumn(type, "Тип книги");
        type.setSortable(true);
//        addSetTypeColumn(cellTable);

        TextColumn<MyBook> count = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) {
                return String.valueOf(object.getCount());
            }
        };
        cellTable.addColumn(count, "Количество");
        count.setSortable(true);
//        addCountColumn(cellTable);

        TextColumn<MyBook> dateOfPublic = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) { return String.valueOf(object.getDateOfPublication()); }
        };
        cellTable.addColumn(dateOfPublic, "Дата публикации");
        dateOfPublic.setSortable(true);
//        addDateOfPublicColumn(cellTable);

        TextColumn<MyBook> dateOfAdded = new TextColumn<MyBook>() {
            @Override
            public String getValue(MyBook object) { return String.valueOf(object.getDateOfAdded()); }
        };
        cellTable.addColumn(dateOfAdded, "Дата добавления");
        dateOfAdded.setSortable(true);
//        addDateOfAddedColumn(cellTable);

        //Compare columns
        ListDataProvider<MyBook> dataProvider = new ListDataProvider<>();
        dataProvider.addDataDisplay(cellTable);
        List<MyBook> list = dataProvider.getList();
        list.addAll(myBookList);
        ColumnSortEvent.ListHandler<MyBook> columnSortHandler = new ColumnSortEvent.ListHandler<>(list);
        columnSortHandler.setComparator(id, MyBook::compareTo);
        columnSortHandler.setComparator(author, MyBook::compareAuthor);
        columnSortHandler.setComparator(title, MyBook::compareTitle);
        columnSortHandler.setComparator(pageAmount, MyBook::comparePageAmount);
        columnSortHandler.setComparator(dateOfPublic, Comparator.comparing(MyBook::getDateOfPublication));
        columnSortHandler.setComparator(dateOfAdded, Comparator.comparing(MyBook::getDateOfAdded));
        columnSortHandler.setComparator(type, Comparator.comparing(MyBook::getTypeEnum));
        columnSortHandler.setComparator(count, MyBook::compareCount);
        cellTable.addColumnSortHandler(columnSortHandler);

        // We know that the data is sorted alphabetically by default.
        cellTable.getColumnSortList().push(author);


//        cellTable.addColumnSortHandler(sortHandler);
        cellTable.setWidth("100%", true);
        cellTable.setRowCount(myBookList.size(), true);
        cellTable.setRowData(0, myBookList);
    }

    private void createPanel() {
        tbId.setText(String.valueOf(myBookList.size() + 1));

        tbAuthor.setText("Автор");
        tbTitle.setText("Название");
        tbPageAmount.setText("Количество страниц");
        tbDateOfPublic.setText("Дата публикации");
        for (BookType bt : BookType.values()) { lbType.addItem(String.valueOf(bt)); }
        tbCount.setText("Количество в наличии");

        hpAdded.add(tbId);
        hpAdded.add(tbAuthor);
        hpAdded.add(tbTitle);
        hpAdded.add(tbPageAmount);
        hpAdded.add(lbType);
        hpAdded.add(tbCount);
        hpAdded.add(tbDateOfPublic);
        hpAdded.setWidth("87%");

        vpBut.add(bAdd);
        vpBut.add(bDelete);

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
        }
    }

}
