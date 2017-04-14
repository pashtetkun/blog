const React = require('react');
const Component = React.Component;
const Lightbox = require('react-images');
const IMAGES = require('../constants').IMAGES;

class Gallery extends Component {

  constructor() {
    super();
    this.state = {
			lightboxIsOpen: false,
			currentImage: 0,
		};
    this.closeLightbox = this.closeLightbox.bind(this);
		this.gotoNext = this.gotoNext.bind(this);
		this.gotoPrevious = this.gotoPrevious.bind(this);
		this.gotoImage = this.gotoImage.bind(this);
		this.handleClickImage = this.handleClickImage.bind(this);
		this.openLightbox = this.openLightbox.bind(this);
  }

  openLightbox (index, event) {
		event.preventDefault();
		this.setState({
			currentImage: index,
			lightboxIsOpen: true,
		});
	}

	closeLightbox () {
		this.setState({
			currentImage: 0,
			lightboxIsOpen: false,
		});
	}

	gotoPrevious () {
		this.setState({
			currentImage: this.state.currentImage - 1,
		});
	}

	gotoNext () {
		this.setState({
			currentImage: this.state.currentImage + 1,
		});
	}

	gotoImage (index) {
		this.setState({ currentImage: index });
	}

	handleClickImage () {
		if (this.state.currentImage === this.props.images.length - 1) return;
		this.gotoNext();
	}

  renderGallery() {
    return IMAGES.map((image, index) => {
      return (
        <div key = {index} className = 'col-lg-3 col-md-4 col-xs-6 thumb'>
          <a href = '#'
            className = 'thumbnail image-in-gallery'
            onClick={(e) => this.openLightbox(index, e)}>
              <img className = 'image-in-gallery' src = {image.src} ></img>
          </a>
        </div>
      )
    })
  }

  render() {
    return (
      <div>
        {this.renderGallery()}
        <Lightbox
          currentImage={this.state.currentImage}
          images={IMAGES}
          isOpen={this.state.lightboxIsOpen}
          onClickPrev={this.gotoPrevious}
          onClickNext={this.gotoNext}
          onClose={this.closeLightbox}
        />
      </div>
    );
  }
};

module.exports = Gallery;
