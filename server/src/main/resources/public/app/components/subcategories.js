const React = require('react');
const Component = React.Component;
const Repo = require('../repo');
const Link = require('react-router').Link;
const Translation = require('../translation');

class Subcategories extends Component {

  componentWillMount() {
    const init = this.props.location.pathname.split('/')[1];
    this.initSubcategories(init);
    this.setState({ selectedSubCategory: this.props.params.subcategory })
  }

  componentWillReceiveProps(nextProps) {
    const prev = this.props.location.pathname.split('/')[1];
    const next = nextProps.location.pathname.split('/')[1];
    if(next != prev) { this.initSubcategories(next); }
    if(nextProps.params.subcategory != this.props.params.subcategory) {
      this.setState({ selectedSubCategory: nextProps.params.subcategory });
    }
  }

  initSubcategories(category) {
    Repo.getAllSubcategories(category).then((data) => {
      this.setState({category, allSubcategories: data});
    });
  }

  renderAllSubcategories() {
    if(this.state.allSubcategories) {
      const state = this.state;
      return state.allSubcategories.map((subcategory, index) => {
        const subcategoryName = Translation[subcategory];
        const isActiveSubCategory = state.selectedSubCategory == subcategory ? 'activeSubCategory' : '';
        return (<Link
                  key = {index}
                  className = {`list-group-item ${isActiveSubCategory}`}
                  to = {`/${this.state.category}/${subcategory}`}>
                  {subcategoryName}
                </Link>)})
    } else {
      return '';
    }
  }

  render() {
    return(
      <div className = 'row'>
        <div className = 'col-md-3'>
          <div className = 'list-group'>
            {this.renderAllSubcategories()}
          </div>
        </div>
        <div className = 'col-md-9'>
          {this.props.children}
        </div>
      </div>
    );
  }
};

module.exports = Subcategories;
