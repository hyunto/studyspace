import React, {
  Component
} from 'react';
import logo from './logo.svg';
import './App.css';
import TodaysPlan from './03/TodaysPlan';

class App extends Component {
  render() {
    return (
      <div className="app">
        <h1 className="title">두잇!리액트 시작하기</h1>
        <TodaysPlan/>
      </div>
    );
  }
}

export default App;