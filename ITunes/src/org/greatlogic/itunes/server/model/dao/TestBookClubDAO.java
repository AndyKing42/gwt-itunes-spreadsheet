package org.greatlogic.itunes.server.model.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.greatlogic.itunes.server.model.dto.BookClub;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.greatlogic.glbase.gllib.GLUtil;

public class TestBookClubDAO implements IBookClubDAO {
//--------------------------------------------------------------------------------------------------
private static int             _nextId = 1;
private static Random          _random = new Random(System.currentTimeMillis());
private static TestBookClubDAO _testBookClubDAO;

private Map<Integer, BookClub> _bookClubMap;
//--------------------------------------------------------------------------------------------------
static TestBookClubDAO getInstance() {
  if (_testBookClubDAO == null) {
    _testBookClubDAO = new TestBookClubDAO();
  }
  return _testBookClubDAO;
} // getInstance()
//--------------------------------------------------------------------------------------------------
private TestBookClubDAO() {
  _bookClubMap = Maps.newTreeMap();
  createTestData();
} // TestBookClubDAO()
//--------------------------------------------------------------------------------------------------
private void createBookClub(final String bookClubName, final String bookClubDesc) {
  int id = _nextId++;
  BookClub bookClub = new BookClub(bookClubDesc, bookClubName, id,
                                   GLUtil.currentTimeYYYYMMDDHHMMSS(), "unknown", 0);
  _bookClubMap.put(id, bookClub);
} // createBookClub()
//--------------------------------------------------------------------------------------------------
private static final String[] Genres            = new String[] {"Adventure", "Art and Design",
    "Autobiography", "Biography", "Business", "Chick Lit", "Classics", "Comics and Graphic Novel",
    "Craft and Hobbies", "Crime Fiction", "Fantasy", "Fiction", "Film", "Health, Mind, and Body",
    "Historical Fiction", "History", "Horror", "House and Garden", "Philosophy", "Poetry",
    "Politics", "Religion", "Romance", "Science and Nature", "Science Fiction", "Short Stories",
    "Sport and Leisure"                         };
private static final String[] Locations         = new String[] {"Ashland", "Axbridge",
    "Balboa Island", "Bellevue", "Belvidere", "Bexleyheath", "Bristol", "Calgary", "Everett",
    "Hampton Hill", "Kirkland", "Leicester", "Long Beach", "Medina", "Mukilteo", "Oadby",
    "Phoenix", "Redmond", "San Francisco", "Santa Barbara", "Scottsdale", "Seattle", "Teddington",
    "Woodinville"                               };
private static final int      NumberOfBookClubs = 50;
private static final String[] Types             = new String[] {"Book Club", "Book Group",
    "Literary Society"                          };

private void createTestData() {
  Set<String> genreLocationSet = Sets.newTreeSet();
  for (int bookClubCount = 0; bookClubCount < NumberOfBookClubs; ++bookClubCount) {
    String genre;
    String location;
    String type;
    boolean duplicateName;
    do {
      genre = Genres[_random.nextInt(Genres.length)];
      type = Types[_random.nextInt(Types.length)];
      location = Locations[_random.nextInt(Locations.length)];
      duplicateName = genreLocationSet.contains(genre + "~" + location);
    } while (duplicateName);
    genreLocationSet.add(location + "~" + genre);
    String bookClubName = location + " " + genre + " " + type;
    String bookClubDesc = genre + " " + type + " of " + location;
    createBookClub(bookClubName, bookClubDesc);
  }
} // createTestData()
//--------------------------------------------------------------------------------------------------
@Override
public BookClub findById(final Integer id) {
  return _bookClubMap.get(id);
} // findById()
//--------------------------------------------------------------------------------------------------
@Override
public void save(final BookClub bookClub) {
  _bookClubMap.put(bookClub.getId(), bookClub);
} // saveBookClub()
//--------------------------------------------------------------------------------------------------
@Override
public List<BookClub> selectAllBookClubs() {
  List<BookClub> result = Lists.newArrayList(_bookClubMap.values());
  Collections.sort(result, new Comparator<BookClub>() {
    @Override
    public int compare(final BookClub bookClub1, final BookClub bookClub2) {
      return bookClub1.getBookClubName().compareToIgnoreCase(bookClub2.getBookClubName());
    } // compare()
  });
  return result;
} // getBookClubList()
//--------------------------------------------------------------------------------------------------
}