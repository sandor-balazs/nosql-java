'use strict';

angular.module('ontimeApp')
    .factory('ProjectCountry', function ($resource, DateUtils) {
        return $resource('api/projectCountrys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
