const React = require('react');
const Component = React.Component;
const Markdown = require('markdown').markdown;
const Repo = require('../repo');
const ScrollToTop = require('react-scroll-up');


class Article extends Component {

  componentWillMount() {
    this.initArticle();
  }

  initArticle() {
    const category = this.props.params.category;
    const subcategory = this.props.params.subcategory;
    const article = this.props.params.article;
    Repo.getArticle(category, subcategory, article).then((data) => {
      this.setState({ articleMD: data });
    })
  }

  createMarkup() {

    if(this.state) {
      return {__html: Markdown.toHTML(this.state.articleMD)};
    }
  }

  render() {
    return(
        <div>
            <div className = 'article' dangerouslySetInnerHTML={this.createMarkup()} />
            <ScrollToTop showUnder={160}>
                <img src="/images/up_arrow_round.png"></img>
            </ScrollToTop>
        </div>
    );
  }
};

module.exports = Article;
