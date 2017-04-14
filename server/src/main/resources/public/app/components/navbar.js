const React = require('react');
const Component = React.Component;
const Link = require('react-router').Link;
const Translation = require('../translation');
const Repo = require('../repo');

class NavBar extends Component {

  componentWillMount() {
    Repo.getAllCategories().then((data) => {
      this.setState({allCategories: data});
    });
  }

  render() {
    const allCategories = this.state ? this.state.allCategories.map((category, index) => {
      const categoryName = Translation[category];
      const isActiveTab = this.props.selectedCategory == category ? 'active activeCategory' : '';
      return (<li className = {isActiveTab} key = {index}>
                <Link to = {`/${category}`}>{categoryName}</Link>
              </li>);
    }) : '';
    return(
      <nav className = 'navbar navbar-default'>
        <div className = 'container-fluid'>
          <div className = 'navbar-header'>
            <button type = 'button' className = 'navbar-toggle collapsed' data-toggle = 'collapse' data-target = '#categories' aria-expanded = 'false'>
              <span className = 'icon-bar'></span>
              <span className = 'icon-bar'></span>
              <span className = 'icon-bar'></span>
            </button>
            <Link className = 'navbar-brand' to = '/'>Pixis1</Link>
          </div>
          <div className = 'collapse navbar-collapse' id = 'categories'>
            <ul className = 'nav navbar-nav'>
              {allCategories}
            </ul>
          </div>
        </div>
      </nav>
    )
  }
}

module.exports = NavBar;
