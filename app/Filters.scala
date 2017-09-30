/**
  * Created by caeus on 25/06/17.
  */

import javax.inject.Inject

import play.api.http.{DefaultHttpFilters, EnabledFilters}
import play.filters.cors.CORSFilter

class Filters @Inject()(
                         defaultFilters: EnabledFilters,
                         cors: CORSFilter
                       ) extends DefaultHttpFilters(defaultFilters.filters :+ cors: _*)