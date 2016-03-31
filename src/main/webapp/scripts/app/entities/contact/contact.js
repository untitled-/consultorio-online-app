'use strict';

angular.module('consultorioOnlineAppApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contact', {
                parent: 'entity',
                url: '/contacts',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.contact.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contact/contacts.html',
                        controller: 'ContactController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contact');
                        $translatePartialLoader.addPart('contactType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('contact.detail', {
                parent: 'entity',
                url: '/contact/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'consultorioOnlineAppApp.contact.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contact/contact-detail.html',
                        controller: 'ContactDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contact');
                        $translatePartialLoader.addPart('contactType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Contact', function($stateParams, Contact) {
                        return Contact.get({id : $stateParams.id});
                    }]
                }
            })
            .state('contact.new', {
                parent: 'contact',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contact/contact-dialog.html',
                        controller: 'ContactDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    contactType: null,
                                    value: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('contact', null, { reload: true });
                    }, function() {
                        $state.go('contact');
                    })
                }]
            })
            .state('contact.edit', {
                parent: 'contact',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contact/contact-dialog.html',
                        controller: 'ContactDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Contact', function(Contact) {
                                return Contact.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contact', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('contact.delete', {
                parent: 'contact',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contact/contact-delete-dialog.html',
                        controller: 'ContactDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Contact', function(Contact) {
                                return Contact.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contact', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
