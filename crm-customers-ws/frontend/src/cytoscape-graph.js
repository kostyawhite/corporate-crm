import {PolymerElement,html} from '@polymer/polymer/polymer-element.js';
import cytoscape from 'cytoscape/dist/cytoscape.esm.min.js';

let cyMap = new Map();

class CytoscapeGraph extends PolymerElement {
    static get template() {
        return html`
            <style>
                #cy {
                    width: 900px;
                    height: 400px;
                    display: block;
                    border: 1px dotted #c8c8c8;
                }
            </style>
            <div id$="[[taskId]]" style="display: none;"></div>
            <div id="cy"></div>`
    }

    static get is() {return 'cytoscape-graph'}

    attached() {

        let cy = cytoscape({

          container: this.shadowRoot.querySelector("#cy"),

          elements: [

          ],

          style: [
            {
              selector: 'node',
              style: {
                'background-color': '#1676F3',
                'label': 'data(id)',
                'color': '#000',
                'font-size': '18px',
                'font-weight': 'normal'
              }
            },


            {
              selector: 'edge',
              style: {
                'width': 3,
                'curve-style': 'bezier',
                'line-color': '#ссс',
                'target-arrow-color': '#666',
                'target-arrow-shape': 'triangle'
              }
            },

            {
               selector: '.current',
               style: {
                 'background-color': '#50c878',
                 'font-weight': 'bold',
               }
             },

             {
                selector: '.previous',
                style: {
                'background-color': '#ff6f61',
               }
             }
          ],

        });

        cy.userPanningEnabled(false);

        return cy;
    }

    ready() {
        super.ready();
        cyMap.set(this.taskId, this.attached());
    }

    setCurrentElement(value) {
        let cy = cyMap.get(this.taskId);
        cy.nodes().removeClass('current');
        cy.$(value).classes('current');
    }

    setPreviousElement(value) {
        let cy = cyMap.get(this.taskId);
        cy.nodes().removeClass('previous');
        cy.$(value).classes('previous');
     }

    setData(nodes, edges) {
        var nodesArray = JSON.parse(nodes);
        var edgesArray = JSON.parse(edges);
        let cy = cyMap.get(this.taskId);
        for (var i = 0; i < nodesArray.nodes.length; i++) {
            cy.add({ group: 'nodes', data: { id: nodesArray.nodes[i].id }});
        }
        for (var i = 0; i < edgesArray.edges.length; i++) {
            cy.add({ group: 'edges', data: { source: edgesArray.edges[i].source, target: edgesArray.edges[i].target }});
        }

        var layout = cy.layout({
            name: 'circle'
        });

        layout.run();
    }
}

customElements.define(CytoscapeGraph.is, CytoscapeGraph);


