const React = require('react');
const Component = React.Component;
const NavBar = require('./navbar');
const Link = require('react-router').Link;

class Main extends Component {
  render() {
    const currentPath = this.props.location.pathname;
    const [ ,selectedCategory, ] = currentPath.split('/');
    return(
      <div className = 'container mycontainer'>
        <div className = 'row'>
          <img className = 'header' src = '/images/gallery/header.jpg'></img>
        </div>
        <div className = 'row'>
          <NavBar selectedCategory = {selectedCategory} />
        </div>
        <div className = 'row'>
          <Link to='/logout' >логаут</Link>
        </div>
        <div className = 'row customrow'>
          {this.props.children}
        </div>
      </div>
    );
  }
}

module.exports = Main;
