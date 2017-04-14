const React = require('react');
const Component = React.Component;
const Repo = require('../repo');

class Logout extends Component {

  constructor() {
    super();
  }

  logoutHandler() {
  	Repo.logout().then((data) => {
      
    });
  }

  render() {
    return (
      <div>
        <a href = '#'
            onClick={(e) => this.logoutHandler()}>
              Логаут22
          </a>
      </div>
    );
  }
};

module.exports = Logout;
