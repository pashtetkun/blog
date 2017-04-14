const React = require('react');
const Component = React.Component;
const Link = require('react-router').Link;
const Repo = require('../repo');

class ListArticles extends Component {

  componentWillMount() {
    const category = this.props.params.category;
    const subcategory = this.props.params.subcategory;
    this.initArticles(category, subcategory);
  }

  componentWillReceiveProps(nextProps) {
    if(this.props != nextProps){
      this.initArticles(nextProps.params.category, nextProps.params.subcategory);
    }
  }

  initArticles(category, subcategory) {
    Repo.getAllArticles(category, subcategory).then((data) => {
      this.setState({category, subcategory, allArticles: data});
    });
  }

  renderListArticles() {
    const state = this.state;
    if(!this.props.children && state && state.allArticles) {
      return (<div className = 'list-group'>{state.allArticles.map((article, index) => {
        return(<Link
          key = {index}
          className = 'list-group-item'
          to = {`/${state.category}/${state.subcategory}/${article}`}>
          {article.replace('.md', '')}
        </Link>)
      })}</div>)
    } else {
      return '';
    }
  }

  render() {
    return(
      <div >
        {this.props.children || this.renderListArticles()}
      </div>
    );
  }
};

module.exports = ListArticles;
