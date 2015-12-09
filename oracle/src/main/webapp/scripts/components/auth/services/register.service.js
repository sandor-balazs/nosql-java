'use strict';

angular.module('ontimeApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


