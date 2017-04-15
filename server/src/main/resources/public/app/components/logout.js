const React = require('react');
const Component = React.Component;
const Repo = require('../repo');

class Logout extends Component {

  constructor() {
    super();
  }

  componentWillMount(){
  	Repo.logout().then((data) => {
  		
    });
  }

  render() {
    return null;
  }
};

module.exports = Logout;
