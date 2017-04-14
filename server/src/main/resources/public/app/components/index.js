const React = require('react');
const ReactDOM = require('react-dom');
const ReactRouter = require('react-router');
const Route = ReactRouter.Route;
const Router = ReactRouter.Router;
const browserHistory = ReactRouter.browserHistory;
const IndexRedirect = ReactRouter.IndexRedirect;
const Main = require('./main');
const Subcategories = require('./subcategories');
const Gallery = require('./gallery');
const ListArticles = require('./list-articles');
const Article = require('./article');

class NotFound extends React.Component {
  render() {
    return(<div>Page Not Found</div>);
  }
}

ReactDOM.render((
  <Router history = {browserHistory}>
    <Route path = '/' component = {Main}>
      <IndexRedirect to = '/articles/all'/>
      <Route path = 'gallery' component = {Gallery}/>
      <Route path = ':category' component = {Subcategories}>
        <IndexRedirect to = 'all' />
        <Route path = ':subcategory' component = {ListArticles}>
          <Route path = ':article' component = {Article}/>
        </Route>
      </Route>
    </Route>
    <Route path = '/*' component = {NotFound} />
  </Router>
), document.getElementById('app'));
